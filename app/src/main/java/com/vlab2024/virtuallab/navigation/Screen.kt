package com.vlab2024.virtuallab.navigation

sealed class Screen(val route: String) {
data object Landing: Screen("landiingScreen")
data object Register: Screen("registerScreen")
data object Login: Screen("loginScreen")
}