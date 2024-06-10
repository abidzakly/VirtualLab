package org.d3ifcool.virtualab.navigation

const val KEY_USER_TYPE= "userType"
const val KEY_USER_ID= "idType"

sealed class Screen(val route: String) {
    data object Landing : Screen("landingScreen")
    data object About : Screen("aboutScreen")
    data object Profile : Screen("profileScreen")
    data object TermsCondition : Screen("termsConditionScreen")

    //    Auth
    data object Role : Screen("roleScreen")
    data object Register : Screen("registerScreen/{$KEY_USER_TYPE}") {
        fun withId(id: Int) = "registerScreen/$id"
    }
    data object Login : Screen("loginScreen")

    //    Guru
    data object GuruDashboard : Screen("guruDashboardScreen")
    data object AddMateri : Screen("addMateriScreen")
    data object  AddSoal : Screen("addSoalScreen")
    data object AddLatihan : Screen("addLatihanScreen")
    data object GuruMateri : Screen("guruMateriScreen")
    data object GuruLatihan : Screen("guruLatihanScreen")
    data object GuruDetailMateri : Screen("guruDetailMateriScreen")
    data object GuruDetailLatihan : Screen("guruDetailLatihanScreen")

    //    Murid
    data object MuridDashboard : Screen("muridDashboardScreen")
    data object Introduction : Screen("introductionScreen")
    data object MuridMateri : Screen("muridMateriScreen")
    data object MuridDetailMateri : Screen("muridDetailMateriScreen")
    data object MuridLatihan : Screen("muridLatihanScreen")
    data object MuridDetailLatihan : Screen("muridDetailLatihanScreen")
    data object Reaksi : Screen("reaksiScreen")
    data object Nilai : Screen("nilaiScreen")
    data object CekJawaban : Screen("cekJawabanScreen")

    // Admin
    data object AdminDashboard : Screen("adminDashboardScreen")
    data object CheckUser : Screen("checkUserScreen")
    data object CheckFile : Screen("checkFileScreen")
    data object UsersInfo : Screen("usersInfoScreen/{$KEY_USER_ID}") {
        fun withId(id: Int) = "usersInfoScreen/$id"
    }
    data object FileInfo : Screen("fileInfoScreen")
    data object ManageIntroContent : Screen("manageContentScreen")
    data object UpdateIntroContent : Screen("updateIntroContentScreen")

}