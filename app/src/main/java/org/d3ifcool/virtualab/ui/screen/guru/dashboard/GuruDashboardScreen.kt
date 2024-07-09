package org.d3ifcool.virtualab.ui.screen.guru.dashboard

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.BottomSheet
import org.d3ifcool.virtualab.ui.component.ContentList
import org.d3ifcool.virtualab.ui.component.GuruEmptyState
import org.d3ifcool.virtualab.ui.component.RiwayatSheet
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.TopNavDashboard
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.utils.UserDataStore

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun GuruDashboardScreen(navController: NavHostController, viewModel: GuruDashboardViewModel) {
    val context = LocalContext.current
    val sheetStateBuat = rememberBottomSheetScaffoldState()
    val sheetStateLihat = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    val dataStore = UserDataStore(context)
    var isPressed by remember { mutableStateOf(false) }
    var fabPressed by remember { mutableStateOf(true) }
    var backgroundColor by remember { mutableStateOf(if (!isPressed) Transparent else Black) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )
    Log.d("isPressedB4", "$isPressed")

    LaunchedEffect(fabPressed) {
        if (!fabPressed) {
            scope.launch {
                sheetStateBuat.bottomSheetState.hide()
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNav(
                Screen.GuruDashboard.route,
                navController,
                isClicked = isPressed
            ) {
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
            Log.d("isPressed", "$isPressed")
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.zIndex(-1f),
                containerColor = DarkBlueDarker,
                onClick = {
                    fabPressed = true
                    backgroundColor = Color.Black
                    scope.launch {
                        sheetStateBuat.bottomSheetState.expand()
                    }
                }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.menu_buat_button),
                    tint = White
                )
            }
        },
        containerColor = White
    ) { padding ->
        Box(modifier = Modifier
            .zIndex(-2f)
            .pullRefresh(refreshState)) {
            ScreenContent(
                modifier = Modifier
                    .padding(padding),
                navController,
                dataStore,
                viewModel
            )
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = White,
                backgroundColor = DarkBlueDarker
            )
        }
        LaunchedEffect(sheetStateBuat.bottomSheetState) {
            snapshotFlow { sheetStateBuat.bottomSheetState.currentValue }
                .collect { bottomSheetValue ->
                    backgroundColor = if (bottomSheetValue == SheetValue.Expanded) {
                        Black
                    } else {
                        Transparent
                    }
                }
        }
        LaunchedEffect(sheetStateLihat.bottomSheetState) {
            snapshotFlow { sheetStateLihat.bottomSheetState.currentValue }
                .collect { bottomSheetValue ->
                    if (bottomSheetValue == SheetValue.Expanded) {
                        backgroundColor = Black
                        isPressed = true
                    } else {
                        backgroundColor = Transparent
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
        BottomSheet(
            scaffoldSheetState = sheetStateBuat,
            title = R.string.fab_slide_up_title,
            action1 = R.string.materi_title,
            onClickAct1 = { navController.navigate(Screen.AddMateri.route) },
            action2 = R.string.latihan,
            onClickAct2 = { navController.navigate(Screen.AddLatihan.route) },
            action3 = R.string.contoh_reaksi_icon_bottomsheet,
            onClickAct3 = { navController.navigate(Screen.AddContohReaksi.route)}
        )
        RiwayatSheet(state = sheetStateLihat, navController = navController)
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    dataStore: UserDataStore,
    viewModel: GuruDashboardViewModel
) {
    val userFullname by dataStore.userFullNameFlow.collectAsState("")
    val context = LocalContext.current
    val combinedPosts by viewModel.combinedPosts.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    Column(modifier = modifier) {
        TopNavDashboard(name = userFullname, navController = navController)
        when (status) {
            ApiStatus.IDLE -> null
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.SUCCESS -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        MediumText(
                            text = stringResource(id = R.string.dashboard_guru_sub_header),
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth()
                        )
                    }
                    if (combinedPosts.isNotEmpty()) {
                        items(combinedPosts) {
                            Log.d("GuruDashboard", "CombinedPost: $it")
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentList(
                                title = it.title,
                                desc = if (it.postType == "Materi") it.description else "Tingkat Kesulitan: ${it.description}",
                                status = it.approvalStatus
                            ) {
                                val route = if (it.postType == "Materi") {
                                    Screen.GuruDetailMateri.withId(it.postId)
                                } else {
                                    if (it.approvalStatus == "DRAFT") {
                                        Screen.AddSoal.withId(it.postId)
                                    } else {
                                        Screen.GuruDetailLatihan.withId(it.postId)
                                    }
                                }
                                viewModel.clearMessage()
                                navController.navigate(route)
                            }
                        }
                    } else {
                        item {
                            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                GuruEmptyState(text = "Anda belum menambahkan data.") {
                                    viewModel.getPosts()
                                }
                            }
                        }
                    }
                }
            }

            ApiStatus.FAILED -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    GuruEmptyState(text = "Gagal Memuat Data") {
                        viewModel.getPosts()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Prev() {
//    GuruDashboardScreen(navController = rememberNavController())
}