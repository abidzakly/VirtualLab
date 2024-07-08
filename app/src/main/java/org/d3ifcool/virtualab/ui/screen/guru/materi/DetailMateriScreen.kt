package org.d3ifcool.virtualab.ui.screen.guru.materi

import android.content.Context
import android.media.MediaMetadataRetriever
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.LoadingStateDialog
import org.d3ifcool.virtualab.ui.component.PopUpDialog
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.component.VideoPlayer

@Composable
fun DetailMateriScreen(navController: NavHostController, viewModel: DetailMateriViewModel) {
    val context = LocalContext.current
    val isDeleting by viewModel.isDeleting.collectAsState()
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

    var materialId by remember { mutableIntStateOf(0) }
    Scaffold(topBar = {
        TopNav(title = R.string.guru_detail_materi_title, navController = navController) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.navigate(Screen.UpdateMateri.withId(materialId)) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Ikon Edit")
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Ikon Delete")
                }
            }
        }
    }, bottomBar = {
        BottomNav(navController = navController)
    }) { padding ->
        ScreenContent(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            onMaterialId = { materialId = it },
            context
        )
        if (showDialog) {
            PopUpDialog(
                onDismiss = { showDialog = false; },
                icon = R.drawable.baseline_warning_amber,
                title = "Anda yakin ingin menghapus materi ini?"
            ) {
                showDialog = false
                isLoading = true
                viewModel.deleteMateri()
            }
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: DetailMateriViewModel,
    onMaterialId: (Int) -> Unit,
    context: Context
) {
    val data by viewModel.materiData.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    var isVideoPlaying by remember {
        mutableStateOf(false)
    }

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            LoadingState()
        }

        ApiStatus.SUCCESS -> {
            val materiItem = data!!.materiItem!!
            val stringUri = ApiService.getMateriMedia(materiItem.materialId)
            onMaterialId(materiItem.materialId)
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RegularText(text = stringResource(id = R.string.judul_materi_guru), fontWeight = FontWeight.SemiBold)
                RegularText(
                    text = materiItem.title,
                    fontWeight = FontWeight.Normal
                )
                RegularText(
                    text = stringResource(R.string.media_pembelajaran),
                    fontWeight = FontWeight.SemiBold
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (materiItem.mediaType == "image") {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(ApiService.getMateriMedia(materiItem.materialId))
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
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isVideoPlaying) Color.Black.copy(alpha = 0.8f) else Color.Transparent)
                                .clickable { isVideoPlaying = true },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!isVideoPlaying) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("$stringUri/thumbnail")
                                        .diskCachePolicy(CachePolicy.DISABLED)
                                        .memoryCachePolicy(CachePolicy.DISABLED)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(id = R.drawable.loading_img),
                                    error = painterResource(id = R.drawable.broken_image),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .aspectRatio(1f)
                                        .padding(4.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.play_button),
                                    contentDescription = "Play Button",
                                    tint = Color.White
                                )
                            } else {
                                VideoPlayer(
                                    media = stringUri,
                                    isUri = false,
                                    appContext = context
                                ) {
                                    isVideoPlaying = it
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 6.dp))
                    RegularText(
                        text = materiItem.filename,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                RegularText(
                    text = stringResource(id = R.string.deskripsi_text),
                    fontWeight = FontWeight.SemiBold
                )
                RegularText(
                    text = materiItem.description,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify
                )
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LargeText(text = "Data tidak ditemukan :(")
                Button(onClick = { viewModel.getMateriDetail() }) {
                    RegularText(text = "Coba Lagi")
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Prev() {
    DetailMateriScreen(rememberNavController(), viewModel())
}