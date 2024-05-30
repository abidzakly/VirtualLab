package org.d3ifcool.virtualab.ui.screen.guru.dashboard

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.BottomSheet
import org.d3ifcool.virtualab.ui.component.TopNavDashboard
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuruDashboardScreen(navController: NavHostController) {
    val sheetStateBuat = rememberBottomSheetScaffoldState()
    val sheetStateLihat = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var backgroundColor by remember { mutableStateOf(Transparent) }
    var isPressed by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNav(
                Screen.GuruDashboard.route,
                navController,
                isClicked = isPressed
            ) {
                scope.launch {
                    sheetStateLihat.bottomSheetState.expand()
                }
            }
            Log.d("isPressed", "$isPressed")
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.zIndex(-1f),
                containerColor = DarkBlueDarker,
                onClick = {
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
        ScreenContent(
            modifier = Modifier
                .padding(padding)
                .zIndex(-2f),
            navController
        )
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
            action1 = R.string.buat_materi_button,
            onClickAct1 = { navController.navigate(Screen.AddMateri.route) },
            action2 = R.string.buat_latihan_button,
            onClickAct2 = {
                navController.navigate(Screen.AddLatihan.route)
            }
        )
        BottomSheet(
            scaffoldSheetState = sheetStateLihat,
            title = R.string.lihat_slide_up_title,
            action1 = R.string.lihat_materi_title,
            onClickAct1 = {
                navController.navigate(Screen.GuruMateri.route) {
                    popUpTo(Screen.GuruDashboard.route)
                }
            },
            action2 = R.string.lihat_latihan_title,
            onClickAct2 = {
                navController.navigate(Screen.GuruLatihan.route) {
                    popUpTo(Screen.GuruDashboard.route)
                }
            }
        )
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        TopNavDashboard(name = "Guru", navController = navController)
        Column(
            modifier = Modifier
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.guru_landing),
                contentDescription = "Dashboard Guru Image"
            )
            Text(
                text = stringResource(R.string.dashboard_guru_sub_header),
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(),
                color = Black
            )
            ItemsList()
            ItemsList()
            ItemsList()
            ItemsList()
        }
    }
}

@Composable
fun ItemsList(content: @Composable (() -> Unit?)? = null) {
    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .background(LightBlue)
            .fillMaxWidth()
            .height(120.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        if (content != null) {
            content()
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Prev() {
    GuruDashboardScreen(navController = rememberNavController())
}