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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
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
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.utils.ViewModelFactory

@Composable
fun GuruLatihanScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val userId by dataStore.userIdFlow.collectAsState(-1)
    val viewModel: LatihanListViewModel? = if (userId != -1) {
        val factory = ViewModelFactory(user_id = userId)
        viewModel(factory = factory)
    } else {
        null
    }

    Scaffold(topBar = {
        TopNav(title = R.string.lihat_latihan_title, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController = navController)
    }) {
        if (viewModel != null) {
            ScreenContent(modifier = Modifier.padding(it), navController, viewModel, userId)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: LatihanListViewModel,
    userId: Int
) {
    val latihanList by viewModel.latihanList.collectAsState()
    var nomorSoal: Int

    val apiStatus by viewModel.apiStatus.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData(userId) }
    )

    when (apiStatus) {
        ApiStatus.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
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
                GuruEmptyState(text = "Belum ada latihan yang ditambahkan")
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
                                status = it.approval_status
                            ) {
                                if (it.approval_status != "DRAFT")
                                    navController.navigate(Screen.GuruDetailLatihan.withId(it.exercise_id))
                                else
                                    navController.navigate(Screen.AddSoal.withId(it.exercise_id))

                            }
                            nomorSoal++
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Prev() {
    GuruLatihanScreen(navController = rememberNavController())
}