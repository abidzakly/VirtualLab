package org.d3ifcool.virtualab.navigation

const val KEY_USER_TYPE = "userRoleType"
const val KEY_USER_ID = "idType"
const val KEY_EXERCISE_ID = "exerciseIdType"
const val KEY_USER_EMAIL = "emailType"

const val KEY_ID_TYPE = "id"
const val KEY_STR_TYPE = "str"

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
    data object UpdateMateri : Screen("addMateriScreen/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "addMateriScreen/$id"
    }
//    Update or Add Soal
    data object AddSoal : Screen("addSoalScreen/{$KEY_EXERCISE_ID}") {
        fun withId(id: Int) = "addSoalScreen/$id"
    }
    data object AddLatihan : Screen("addLatihanScreen")
    data object UpdateLatihan : Screen("addLatihanScreen/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "addLatihanScreen/$id"
    }
    data object GuruMateri : Screen("guruMateriScreen")
    data object GuruLatihan : Screen("guruLatihanScreen")
    data object GuruDetailMateri : Screen("guruDetailMateriScreen/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "guruDetailMateriScreen/$id"
    }
    data object GuruDetailLatihan : Screen("guruDetailLatihanScreen/{$KEY_EXERCISE_ID}") {
        fun withId(id: Int) = "guruDetailLatihanScreen/$id"
    }
    data object GuruContohReaksi : Screen("listContohReaksiScreen")
    data object GuruDetailContohReaksi : Screen("detailContohReaksi/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "detailContohReaksi/$id"
    }
    data object AddContohReaksi : Screen("addContohReaksi")
    data object UpdateContohReaksi : Screen("addContohReaksi/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "addContohReaksi/$id"
    }

    //    Murid
    data object MuridDashboard : Screen("muridDashboardScreen")
    data object Introduction : Screen("introductionScreen")
    data object MuridMateri : Screen("muridMateriScreen")
    data object MuridDetailMateri : Screen("muridDetailMateriScreen/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "muridDetailMateriScreen/$id"
    }
    data object MuridLatihan : Screen("muridLatihanScreen")
    data object MuridDetailLatihan : Screen("muridDetailLatihanScreen/{$KEY_ID_TYPE}/{$KEY_STR_TYPE}") {
        fun withId(id: Int, title: String) = "muridDetailLatihanScreen/$id/$title"
    }
    data object Reaksi : Screen("reaksiScreen")
    data object Nilai : Screen("nilaiScreen")
    data object CekJawaban : Screen("cekJawabanScreen/{$KEY_ID_TYPE}") {
        fun withId(id: Int) = "cekJawabanScreen/$id"
    }

    // Admin
    data object AdminDashboard : Screen("adminDashboardScreen")
    data object CheckUser : Screen("checkUserScreen")
    data object CheckFile : Screen("checkFileScreen")
    data object UsersInfo : Screen("usersInfoScreen/{$KEY_USER_ID}") {
        fun withId(id: Int) = "usersInfoScreen/$id"
    }

    data object FileInfo : Screen("fileInfoScreen/{$KEY_ID_TYPE}/{$KEY_STR_TYPE}") {
        fun withId(id: Int, postType: String) = "fileInfoScreen/$id/$postType"
    }
    data object ManageIntroContent : Screen("manageContentScreen")
    data object UpdateIntroContent : Screen("updateIntroContentScreen")

}