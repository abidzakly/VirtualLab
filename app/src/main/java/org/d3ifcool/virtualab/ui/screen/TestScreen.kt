package org.d3ifcool.virtualab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.MediumText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavHostController
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }
    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sheetState.currentValue) {
        snapshotFlow { sheetState.currentValue }
            .collect { bottomSheetValue ->
                if (bottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
                } else {
                    isPressed = false
                }
            }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            backgroundColor = Color.Black.copy(alpha = 0.5f)
            scope.launch {
                sheetState.show()
            }
        } else {
            backgroundColor = Color.Transparent
            scope.launch {
                sheetState.hide()
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNav(navController = navController) {
                isPressed = !isPressed
            }
        }
    ) { innerPadding ->
        ModalBottomSheetLayout(
            sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            scrimColor = Color.Black.copy(alpha = 0.5f),
            sheetBackgroundColor = Color.White,
            sheetState = sheetState,
            sheetContent = {
                RiwayatSheet(title = R.string.lihat_slide_up_title,
                    action1 = R.string.materi_title,
                    onClickAct1 = {
                        navController.navigate(Screen.GuruMateri.route) {
                            popUpTo(Screen.GuruDashboard.route)
                        }
                    },
                    action2 = R.string.latihan,
                    onClickAct2 = {
                        navController.navigate(Screen.GuruLatihan.route) {
                            popUpTo(Screen.GuruDashboard.route)
                        }
                    },
                    action3 = R.string.contoh_reaksi_icon_bottomsheet,
                    onClickAct3 = {
                        navController.navigate(Screen.GuruContohReaksi.route) {
                            popUpTo(Screen.GuruDashboard.route)
                        }
                    }) {
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .background(backgroundColor)
                    .padding(innerPadding)
            ) {
                Column {
                    Text(text = "Test")
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatSheet(
    title: Int,
    action1: Int,
    onClickAct1: () -> Unit,
    action2: Int,
    onClickAct2: () -> Unit,
    action3: Int,
    onClickAct3: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(701.dp),
//            .background(DarkBlueDarker)
//            .padding(bottom = 180.dp)
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BottomSheetDefaults.DragHandle(color = Color.Black, modifier = Modifier.fillMaxWidth(.25f))
        Spacer(modifier = Modifier.height(16.dp))
        MediumText(
            text = stringResource(title),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 34.dp),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ButtonBottomSheet(
                action = action1,
                onClickAct = onClickAct1,
                image = R.drawable.latihan_icon,
                imageDesc = "Ikon latihan"
            )
            ButtonBottomSheet(
                action = action2,
                onClickAct = onClickAct2,
                image = R.drawable.materi_icon,
                imageDesc = "Ikon latihan"
            )
            ButtonBottomSheet(
                action = action3,
                onClickAct = onClickAct3,
                image = R.drawable.contoh_reaksi_icon,
                imageDesc = "Ikon contoh reaksi"
            )
        }
    }
}

@Composable
private fun ButtonBottomSheet(action: Int, onClickAct: () -> Unit, image: Int, imageDesc: String) {
    Button(
        onClick = { onClickAct() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(LightBlue),
        modifier = Modifier.fillMaxHeight(0.3f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(image),
                contentDescription = imageDesc,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegularText(
                text = stringResource(action),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Test2(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueDarker)
    ) {
            Column(Modifier.fillMaxWidth()) {
                RegularText(text = "asjdhuashd")
            }
            Column(
                modifier = Modifier
                        .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                RegularText(text = "asjdhuashd")
            }
    }
}

@Preview
@Composable
private fun Test() {
    Test2()
}
//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    BottomNavigation {
//        BottomNavigationItem(
//            icon = { Icon(Icons.Default.Home, contentDescription = null) },
//            label = { Text("Home") },
//            selected = false,
//            onClick = { /* Handle navigation here */ }
//        )
//        BottomNavigationItem(
//            icon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
//            label = { Text("Profile") },
//            selected = false,
//            onClick = { /* Handle navigation here */ }
//        )
//        // Add other navigation items as needed
//    }
//}