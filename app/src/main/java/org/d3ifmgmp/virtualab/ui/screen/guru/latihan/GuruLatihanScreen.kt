package org.d3ifmgmp.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifmgmp.virtualab.R
import org.d3ifmgmp.virtualab.navigation.Screen
import org.d3ifmgmp.virtualab.ui.component.BottomNav
import org.d3ifmgmp.virtualab.ui.component.ContentList
import org.d3ifmgmp.virtualab.ui.component.GuruEmptyState
import org.d3ifmgmp.virtualab.ui.component.RegularText
import org.d3ifmgmp.virtualab.ui.component.TopNav

@Composable
fun GuruLatihanScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.lihat_latihan_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController = navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    val viewModel: GuruLatihanViewModel = viewModel()
    val data = viewModel.data

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = if (data.isNotEmpty()) Arrangement.Top else Arrangement.Center,
    ) {
        if (data.isEmpty()) {
            GuruEmptyState(text = "Belum ada latihan yang ditambahkan")
        } else {
            RegularText(
                text = "Latihan yang pernah ditambahkan: ",
                modifier = Modifier.padding(start = 16.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                ContentList(title = "Latihan 1", desc = "lorem ipsum dolor sit ametasdasd") {

                }
                ContentList(title = "Latihan 2", desc = "lorem ipsum dolor sit ametasdasd") {

                }
                ContentList(title = "Latihan 3", desc = "lorem ipsum dolor sit ametasdasd") {

                }
            }
        }
    }
}

@Preview
@Composable
private fun Prev() {
    GuruLatihanScreen(navController = rememberNavController())
}