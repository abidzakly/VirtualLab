package org.d3ifcool.virtualab.ui.screen.guru.contohReaksi

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.LoadingStateDialog
import org.d3ifcool.virtualab.ui.component.PopUpDialog
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav

@Composable
fun DetailContohReaksi(navController: NavHostController, viewModel: DetailContohReaksiViewModel) {
    val context = LocalContext.current
    val isDeleting by viewModel.deleteStatus.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    when (isDeleting) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            LoadingStateDialog()
        }

        ApiStatus.SUCCESS -> {
            isLoading = false
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            viewModel.clearStatus()
        }

        ApiStatus.FAILED -> {
            isLoading = false
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearStatus()
        }
    }

    var articleId by remember { mutableIntStateOf(0) }
    Scaffold(topBar = {
        TopNav(title = R.string.detail_konten, navController = navController) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.navigate(Screen.UpdateContohReaksi.withId(articleId)) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Ikon Edit")
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Ikon Delete")
                }
            }
        }
    }, bottomBar = {
        BottomNav(navController = navController)
    }, containerColor = Color.White
    ) { padding ->
        ScreenContent(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            onArticleId = { articleId = it },
            context
        )
        if (showDialog) {
            PopUpDialog(
                onDismiss = { showDialog = false; },
                icon = R.drawable.baseline_warning_amber,
                title = "Anda yakin ingin menghapus konten ini?"
            ) {
                showDialog = false
                isLoading = true
                viewModel.deleteArticle()
            }
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: DetailContohReaksiViewModel,
    onArticleId: (Int) -> Unit,
    context: Context
) {
    val data by viewModel.articleData.collectAsState()
    val status by viewModel.apiStatus.collectAsState()

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            LoadingState()
        }

        ApiStatus.SUCCESS -> {
            val articleItem = data!!.artikelItem!!
            onArticleId(articleItem.articleId)
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RegularText(text = stringResource(id = R.string.judul_konten_contoh_reaksi), fontWeight = FontWeight.SemiBold)
                RegularText(
                    text = articleItem.title,
                    fontWeight = FontWeight.Normal
                )
                RegularText(
                    text = stringResource(R.string.media_konten_contoh_reaksi),
                    fontWeight = FontWeight.SemiBold
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(ApiService.getArticleMedia(articleItem.articleId))
                                    .diskCachePolicy(CachePolicy.DISABLED)
                                    .memoryCachePolicy(CachePolicy.DISABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.loading_img),
                                error = painterResource(id = R.drawable.broken_image),
                                modifier = Modifier
                                    .size(225.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    Spacer(modifier = Modifier.padding(vertical = 6.dp))
                    RegularText(
                        text = articleItem.filename,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                RegularText(
                    text = stringResource(id = R.string.deskripsi_text),
                    fontWeight = FontWeight.SemiBold
                )
                RegularText(
                    text = articleItem.description,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify
                )
            }
        }

        ApiStatus.FAILED -> {
            GuruEmptyState(text = "Gagal memuat data.") {
                viewModel.getArticleDetail()
            }
        }
    }
}