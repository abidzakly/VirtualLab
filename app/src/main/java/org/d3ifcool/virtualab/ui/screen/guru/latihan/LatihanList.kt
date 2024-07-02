package org.d3ifcool.virtualab.ui.screen.guru.latihan

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ContentList
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.utils.ErrorMessage
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.utils.ViewModelFactory

@Composable
fun GuruLatihanScreen(navController: NavHostController, viewModel: LatihanListViewModel) {
    Scaffold(topBar = {
        TopNav(title = R.string.lihat_latihan_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController = navController)
    }) {
            ScreenContent(modifier = Modifier.padding(it), navController, viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: LatihanListViewModel
) {
    val latihanList by viewModel.latihanList.collectAsState()
    var nomorSoal: Int

    val apiStatus by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMsg.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    when (apiStatus) {
        ApiStatus.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkBlueDarker)
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (errorMessage == ErrorMessage.applicationError) {
                    GuruEmptyState(text = "Terjadi kesalahan, Harap Coba Lagi")
                    Button(onClick = { viewModel.getMyLatihan() }) {
                        RegularText(text = "Coba Lagi")
                    }
                } else {
                    GuruEmptyState(text = "Belum ada latihan yang ditambahkan")
                }
            }
        }

        ApiStatus.SUCCESS -> {
            Box(modifier = modifier.pullRefresh(refreshState)) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    RegularText(
                        text = "Latihan yang pernah ditambahkan: ",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        nomorSoal = 0
                        items(latihanList!!) {
                            ContentList(
                                title = "Latihan ${nomorSoal + 1}",
                                desc = it.title,
                                status = it.approvalStatus
                            ) {
                                if (it.approvalStatus != "DRAFT")
                                    navController.navigate(Screen.GuruDetailLatihan.withId(it.exerciseId))
                                else
                                    navController.navigate(Screen.AddSoal.withId(it.exerciseId))

                            }
                            nomorSoal++
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

        ApiStatus.IDLE -> null
    }
}

@Preview
@Composable
private fun Prev() {
//    GuruLatihanScreen(navController = rememberNavController())
}