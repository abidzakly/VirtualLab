package org.d3ifcool.virtualab.ui.screen.guru.contohReaksi

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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ContentList
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.RiwayatSheet
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriViewModel
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.utils.GenericMessage

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListContohReaksiScreen(navController: NavHostController, viewModel: GuruContohReaksiViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val sheetStateLihat = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(if (!isPressed) Color.Transparent else Color.Black) }

    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )

    Scaffold(topBar = {
        TopNav(title = R.string.riwayat_konten, navController = navController)
    }, bottomBar = {
        BottomNav(currentRoute = Screen.GuruMateri.route, navController, isClicked = isPressed) {
            backgroundColor = Color.Black
            if (isPressed) {
                scope.launch {
                    sheetStateLihat.bottomSheetState.hide()
                }
            } else {
                scope.launch {
                    sheetStateLihat.bottomSheetState.expand()
                }
            }
        }
    },
        containerColor = Color.White
    ) {
        Box(modifier = Modifier
            .pullRefresh(refreshState)
            .padding(it)) {
            ScreenContent(modifier = Modifier, navController, viewModel)
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Color.White,
                backgroundColor = DarkBlueDarker
            )
            LaunchedEffect(sheetStateLihat.bottomSheetState) {
                snapshotFlow { sheetStateLihat.bottomSheetState.currentValue }
                    .collect { bottomSheetValue ->
                        if (bottomSheetValue == SheetValue.Expanded) {
                            backgroundColor = Color.Black
                            isPressed = true
                        } else {
                            backgroundColor = Color.Transparent
                            isPressed = false
                        }
                    }
            }
            Box(
                modifier = Modifier
                    .alpha(0.22f)
                    .fillMaxSize()
                    .background(backgroundColor)
            )
            RiwayatSheet(state = sheetStateLihat, navController = navController)
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: GuruContohReaksiViewModel
) {
    val data by viewModel.articles.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    when (status) {
        ApiStatus.IDLE -> null
        ApiStatus.LOADING -> {
            LoadingState()
        }

        ApiStatus.SUCCESS -> {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Top
            ) {
                MediumText(text = "Konten yang pernah ditambahkan: ")
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn() {
                    items(data) {
                        Spacer(modifier = Modifier.height(8.dp))
                        ContentList(
                            title = it.title,
                            desc = it.description,
                            status = it.approvalStatus
                        ) {
                            navController.navigate(Screen.GuruDetailContohReaksi.withId(it.articleId))
                        }
                    }
                }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = modifier
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (errorMessage == GenericMessage.applicationError) {
                    GuruEmptyState(text = "Terjadi kesalahan, Harap Coba Lagi") {
                        viewModel.getMyMateri()
                    }
                } else {
                    GuruEmptyState(text = stringResource(id = R.string.list_latihan_kosong)) {
                        viewModel.getMyMateri()
                    }
                }
            }
        }
    }
}