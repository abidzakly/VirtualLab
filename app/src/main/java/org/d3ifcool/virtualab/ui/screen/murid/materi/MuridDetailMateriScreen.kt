package org.d3ifcool.virtualab.ui.screen.murid.materi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue2

@Composable
fun MuridDetailMateriScreen(
    navController: NavHostController,
    viewModel: MuridDetailMateriViewModel
) {
    Scaffold(topBar = {
        TopNav(title = R.string.empty_title, navController = navController)
    },
        bottomBar = {
            BottomNav(navController = navController)
        }) {
        ScreenContent(modifier = Modifier.padding(it), viewModel)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, viewModel: MuridDetailMateriViewModel) {
    val materi by viewModel.materi.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 21.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(LightBlue2)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (status == ApiStatus.LOADING) Arrangement.Center else Arrangement.Top
    ) {
        item {
            when (status) {
                ApiStatus.IDLE -> null
                ApiStatus.LOADING -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = DarkBlueDarker)
                    }
                }

                ApiStatus.SUCCESS -> {
                    val it = materi!!.materiItem!!
                    LargeText(
                        text = it.title,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 23.dp)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Image(
                        painter = painterResource(id = R.drawable.rumus_kimia),
                        contentDescription = "Rumus Kimia",
                        modifier = Modifier.size(271.dp, 18.dp)
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    DetailContent(content = it.filename, materialId = it.materialId)
//                DetailContent(content = "Molekul O2", image = R.drawable.gambar_molekul)
//                DetailContent(content = "Molekul CO2", image = R.drawable.gambar_molekul)

                    RegularText(
                        text = it.description,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(horizontal = 22.dp, vertical = 23.dp)
                    )
                }

                ApiStatus.FAILED -> {
                    MuridEmptyState(text = "Gagal memuat data.") {
                        viewModel.getMateriDetail()
                    }
                }
            }
        }

    }
}

@Composable
private fun DetailContent(content: String, materialId: Int) {
    RegularText(
        text = stringResource(id = R.string.detail_materi_sub_header),
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp)
    )
    Spacer(modifier = Modifier.height(18.dp))
    RegularText(
        text = content,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(ApiService.getMateriContent(materialId))
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
    Spacer(modifier = Modifier.height(24.dp))
}

@Preview
@Composable
private fun Prev() {
//    MuridDetailMateriScreen(rememberNavController())
}