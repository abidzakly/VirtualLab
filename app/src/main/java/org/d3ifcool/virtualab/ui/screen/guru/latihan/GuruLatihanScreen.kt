package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav

@Composable
fun GuruLatihanScreen(navController: NavHostController) {
    Scaffold(bottomBar = {
        BottomNav(currentRoute = Screen.GuruLatihan.route, navController = navController)
    }) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(modifier) {

    }
}

@Preview
@Composable
private fun Prev() {
    GuruLatihanScreen(navController = rememberNavController())
}