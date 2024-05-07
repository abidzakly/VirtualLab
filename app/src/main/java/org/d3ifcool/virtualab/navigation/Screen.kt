package org.d3ifcool.virtualab.navigation

const val KEY_ID_USER= "idUser"

sealed class Screen(val route: String) {
    data object Landing : Screen("landingScreen")
    data object Register : Screen("registerScreen/{$KEY_ID_USER}") {
        fun withId(id: Long) = "registerScreen/$id"
    }

    data object Login : Screen("loginScreen")
    data object Dashboard : Screen("dashboardScreen")
    data object Role : Screen("roleScreen")
}