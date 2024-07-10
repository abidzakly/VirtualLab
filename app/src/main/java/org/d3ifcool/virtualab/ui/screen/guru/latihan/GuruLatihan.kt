package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomModalSheet
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ContentList
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.utils.GenericMessage

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GuruLatihanScreen(navController: NavHostController, viewModel: GuruLatihanViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    val sheetStateLihat = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sheetStateLihat.currentValue) {
        snapshotFlow { sheetStateLihat.currentValue }
            .collect { bottomSheetValue ->
                if (bottomSheetValue == ModalBottomSheetValue.Hidden) {
                    isPressed = false
                }
            }
    }
    LaunchedEffect(isPressed) {
        if (isPressed) {
            scope.launch {
                sheetStateLihat.show()
            }
        } else {
            scope.launch {
                sheetStateLihat.hide()
            }
        }
    }

    Scaffold(topBar = {
        TopNav(title = R.string.lihat_latihan_title, navController = navController)
    },
        floatingActionButton = {
            if (!isPressed) {
                FloatingActionButton(
                    containerColor = DarkBlueDarker,
                    onClick = {
                        navController.navigate(Screen.AddLatihan.route)
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.menu_buat_button),
                        tint = Color.White
                    )
                }
            }
        }, bottomBar = {
            BottomNav(
                currentRoute = Screen.GuruLatihan.route,
                navController = navController,
                isClicked = isPressed
            ) {
                isPressed = !isPressed
            }
        },
        containerColor = Color.White
    ) {
        BottomModalSheet(
            sheetState = sheetStateLihat,
            title = R.string.lihat_slide_up_title,
            action1 = R.string.materi_title,
            onClickAct1 = { navController.navigate(Screen.GuruMateri.route) },
            action2 = R.string.latihan,
            onClickAct2 = { navController.navigate(Screen.GuruLatihan.route) },
            action3 = R.string.contoh_reaksi_icon_bottomsheet,
            onClickAct3 = { navController.navigate(Screen.GuruContohReaksi.route) }) {
            Box(
                modifier = Modifier
                    .pullRefresh(refreshState)
                    .padding(it)
            ) {
                ScreenContent(modifier = Modifier, navController, viewModel)
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = Color.White,
                    backgroundColor = DarkBlueDarker
                )
            }
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: GuruLatihanViewModel
) {
    val latihanList by viewModel.latihanDetailList.collectAsState()

    val apiStatus by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMsg.collectAsState()


    when (apiStatus) {
        ApiStatus.LOADING -> {
            LoadingState()
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (errorMessage == GenericMessage.applicationError) {
                    GuruEmptyState(text = "Terjadi kesalahan, Harap Coba Lagi") {
                        viewModel.getMyLatihan()
                    }
                } else {
                    GuruEmptyState(text = "Belum ada latihan yang ditambahkan") {
                        viewModel.getMyLatihan()
                    }
                }
            }
        }

        ApiStatus.SUCCESS -> {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Top
            ) {
                MediumText(text = "Latihan yang pernah ditambahkan: ")
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn() {
                    items(latihanList!!) {
                        Spacer(modifier = Modifier.height(8.dp))
                        ContentList(
                            title = it.title,
                            desc = "Tingkat Kesulitan: ${it.difficulty}",
                            status = it.approvalStatus
                        ) {
                            if (it.approvalStatus != "DRAFT")
                                navController.navigate(Screen.GuruDetailLatihan.withId(it.exerciseId))
                            else
                                navController.navigate(Screen.AddSoal.withId(it.exerciseId))

                        }
                    }
                }
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