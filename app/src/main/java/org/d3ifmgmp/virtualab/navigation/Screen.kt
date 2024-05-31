package org.d3ifmgmp.virtualab.navigation

const val KEY_ID_USER= "idUser"

sealed class Screen(val route: String) {
    data object Landing : Screen("landingScreen")
    data object About : Screen("aboutScreen")
    data object Profile : Screen("profileScreen")
    data object TermsCondition : Screen("termsConditionScreen")

    //    Auth
    data object Role : Screen("roleScreen")
    data object Register : Screen("registerScreen/{$KEY_ID_USER}") {
        fun withId(id: Long) = "registerScreen/$id"
    }
    data object Login : Screen("loginScreen")

    //    Guru
    data object GuruDashboard : Screen("guruDashboardScreen")
    data object AddMateri : Screen("addMateriScreen")
    data object AddLatihan : Screen("addMateriScreen")
    data object GuruMateri : Screen("guruMateriScreen")
    data object GuruLatihan : Screen("guruLatihanScreen")

    //    Murid
    data object MuridDashboard : Screen("muridDashboardScreen")
    data object Introduction : Screen("introductionScreen")
    data object MuridMateri : Screen("muridMateriScreen")
    data object MuridDetailMateri : Screen("muridDetailMateriScreen")
    data object MuridLatihan : Screen("muridLatihanScreen")
    data object MuridDetailLatihan : Screen("muridDetailLatihanScreen")
    data object Reaksi : Screen("reaksiScreen")
    data object Nilai : Screen("nilaiScreen")
}