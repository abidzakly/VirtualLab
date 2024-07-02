package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker

@Composable
fun DetailMateriScreen(navController: NavHostController, viewModel: DetailMateriViewModel) {
    Scaffold(topBar = {
        TopNav(title = R.string.guru_detail_materi_title, navController = navController) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Ikon Edit")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Ikon Delete")
                }
            }
        }
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruMateri.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), viewModel)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, viewModel: DetailMateriViewModel) {
    val data by viewModel.materiData.collectAsState()
    val materiItem = data!!.materiItem!!
    val status by viewModel.apiStatus.collectAsState()

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkBlueDarker)
            }
        }

        ApiStatus.SUCCESS -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RegularText(text = materiItem.title, fontWeight = FontWeight.SemiBold)
                RegularText(
                    text = stringResource(id = R.string.judul_materi_data),
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
                                    .data(ApiService.getContent(materiItem.materialId))
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
                        //                Image(
//                    modifier = Modifier.size(90.dp),
//                    painter = painterResource(R.drawable.media_example),
//                    contentDescription = "File Media Pembelajaran"
//                )
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
                Button(onClick = { viewModel.getMateri() }) {
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