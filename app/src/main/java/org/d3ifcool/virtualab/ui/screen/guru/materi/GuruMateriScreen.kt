package org.d3ifcool.virtualab.ui.screen.guru.materi

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ContentList
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.Poppins

@Composable
fun GuruMateriScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopNav(title = R.string.lihat_materi_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruMateri.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    val viewModel: GuruMateriViewModel = viewModel()
    val data = viewModel.data
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = if (data.isNotEmpty()) Arrangement.Top else Arrangement.Center,
    ) {
        if (data.isEmpty()) {
            GuruEmptyState(text = stringResource(id = R.string.list_latihan_kosong))
        } else {
            Text(
                text = "Materi yang pernah ditambahkan :",
                Modifier.padding(start = 16.dp),
                fontSize = 16.sp,
                fontFamily = Poppins
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                ContentList(title = "Materi 1", desc = "lorem ipsum dolor sit amet", status = "PENDING") {
                    navController.navigate(Screen.GuruDetailMateri.route)
                }
                ContentList(title = "Materi 2", desc = "lorem ipsum dolor sit amet", "APPROVED") {
                    navController.navigate(Screen.GuruDetailMateri.route)
                }
                ContentList(title = "Materi 3", desc = "lorem ipsum dolor sit amet", "DRAFT") {
                    navController.navigate(Screen.GuruDetailMateri.route)
                }
                ContentList(title = "Materi 4", desc = "lorem ipsum dolor sit amet", "REJETED") {
                    navController.navigate(Screen.GuruDetailMateri.route)
                }
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LihatMateriPrev() {
    GuruMateriScreen(rememberNavController())
}