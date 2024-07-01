package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ContentList
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.Poppins
import org.d3ifcool.virtualab.utils.ErrorMessage

@Composable
fun GuruMateriScreen(navController: NavHostController, viewModel: GuruMateriViewModel) {
    Scaffold(topBar = {
        TopNav(title = R.string.lihat_materi_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruMateri.route, navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it), navController, viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: GuruMateriViewModel
) {
    val data by viewModel.materials.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkBlueDarker)
            }
        }

        ApiStatus.SUCCESS -> {
            Box(modifier = modifier.pullRefresh(refreshState)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = "Materi yang pernah ditambahkan :",
                        Modifier.padding(start = 16.dp),
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        items(data) {
                            ContentList(
                                title = it.materiItem!!.title,
                                desc = it.materiItem.description,
                                status = it.materiItem.approvalStatus
                            ) {
                                navController.navigate(Screen.GuruDetailMateri.withId(it.materiItem.materialId))
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = Color.White,
                    backgroundColor = DarkBlueDarker
                )
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (errorMessage == ErrorMessage.applicationError) {
                    GuruEmptyState(text = "Terjadi kesalahan, Harap Coba Lagi")
                    Button(onClick = { viewModel.getMyMateri() }) {
                        RegularText(text = "Coba Lagi")
                    }
                } else {
                    GuruEmptyState(text = stringResource(id = R.string.list_latihan_kosong))
                }
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LihatMateriPrev() {
//    GuruMateriScreen(rememberNavController())
}