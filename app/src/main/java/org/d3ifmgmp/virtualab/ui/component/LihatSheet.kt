package org.d3ifmgmp.virtualab.ui.component

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.d3ifmgmp.virtualab.R
import org.d3ifmgmp.virtualab.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LihatSheet(state: BottomSheetScaffoldState, navController: NavHostController) {
    BottomSheet(
        scaffoldSheetState = state,
        title = R.string.lihat_slide_up_title,
        action1 = R.string.lihat_materi_title,
        onClickAct1 = {
            navController.navigate(Screen.GuruMateri.route) {
                popUpTo(Screen.GuruDashboard.route)
            }
        },
        action2 = R.string.lihat_materi_title,
        onClickAct2 = {
            navController.navigate(Screen.GuruLatihan.route) {
                popUpTo(Screen.GuruDashboard.route)
            }
        }
    )
}