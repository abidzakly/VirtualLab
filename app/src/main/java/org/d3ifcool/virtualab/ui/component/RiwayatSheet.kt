package org.d3ifcool.virtualab.ui.component

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatSheet(state: BottomSheetScaffoldState, navController: NavHostController) {
    BottomSheet(
        scaffoldSheetState = state,
        title = R.string.lihat_slide_up_title,
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
        }
    )
}