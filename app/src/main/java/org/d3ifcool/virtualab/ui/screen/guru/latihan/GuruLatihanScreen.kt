package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.model.Materi
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriViewModel
import org.d3ifcool.virtualab.ui.theme.LightBlueGradient
import org.d3ifcool.virtualab.ui.theme.Poppins

@Composable
fun GuruLatihanScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.lihat_latihan_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController = navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}
@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val viewModel: GuruLatihanViewModel = viewModel()
    val data = viewModel.data

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.empty_state_guru),
                contentDescription = stringResource(R.string.gambar_empty_state_guru)
            )
            RegularText(text = stringResource(R.string.list_latihan_kosong), align = TextAlign.Center)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(4.dp)
        ) {
            Text(
                text = "Latihan yang pernah ditambahkan :",
                Modifier.padding(start = 16.dp),
                fontSize = 16.sp
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                ListMateri(
                    title = R.string.latihan_title,
                    titledata = R.string.materi_title_data
                ) {
                    navController.navigate(Screen.GuruDetailLatihan.route)
                }
                ListMateri(
                    title = R.string.materi_title,
                    titledata = R.string.materi_title_data
                ) {
                    navController.navigate(Screen.GuruDetailLatihan.route)
                }
            }
        }
    }

}

@Composable
fun ListMateri(materi: Materi? = null, title: Int, titledata: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
            .background(LightBlueGradient)
            .padding(24.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Text(
                text = stringResource(titledata),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Icon(painter = painterResource(R.drawable.arrow_circle), contentDescription = "Lihat Materi", modifier = Modifier.size(28.dp))
    }
    Spacer(modifier = Modifier.height(16.dp))
}
@Preview
@Composable
private fun Prev() {
    GuruLatihanScreen(navController = rememberNavController())
}