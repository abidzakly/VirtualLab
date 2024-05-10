package org.d3ifcool.virtualab.ui.screen.murid.nilai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.virtualab.ui.component.BottomNav

@Composable
fun NilaiScreen(navController: NavHostController, route: String? = "") {
    val currentRoute by remember { mutableStateOf(route!!) }
    Scaffold(bottomBar = {
        BottomNav(currentRoute = currentRoute, navController)
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
private fun NilaiScrenPrev() {
    NilaiScreen(rememberNavController())
}