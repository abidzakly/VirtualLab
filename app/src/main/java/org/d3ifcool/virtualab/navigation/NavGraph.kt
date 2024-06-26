package org.d3ifcool.virtualab.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3ifcool.virtualab.ui.screen.AboutScreen
import org.d3ifcool.virtualab.ui.screen.admin.dashboard.AdminDashboardScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.CheckFileScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUserScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.FileInfoScreen
import org.d3ifcool.virtualab.ui.screen.admin.introduction.ManageContentScreen
import org.d3ifcool.virtualab.ui.screen.admin.introduction.UpdateIntroContentScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UsersInfoScreen
import org.d3ifcool.virtualab.ui.screen.murid.dashboard.MuridDashboardScreen
import org.d3ifcool.virtualab.ui.screen.murid.introduction.IntroductionScreen
import org.d3ifcool.virtualab.ui.screen.LandingScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.MuridLatihanScreen
import org.d3ifcool.virtualab.ui.screen.RegisterScreen
import org.d3ifcool.virtualab.ui.screen.LoginScreen
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddSoalScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.DetailLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.GuruLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.AddMateriScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.DetailMateriScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.CekJawabanScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.MuridDetailLatihanScreen
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridDetailMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.nilai.NilaiScreen
import org.d3ifcool.virtualab.ui.screen.ProfileScreen
import org.d3ifcool.virtualab.ui.screen.murid.reaksi.ReaksiScreen
import org.d3ifcool.virtualab.ui.screen.RoleScreen
import org.d3ifcool.virtualab.ui.screen.TermsConditionScreen
import org.d3ifcool.virtualab.utils.UserDataStore

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val userDataStore = UserDataStore(LocalContext.current)
    val userType by userDataStore.userTypeFlow.collectAsState(-1)
    val isLoggedIn by userDataStore.loginStatusFlow.collectAsState(false)
    Log.d("NavGraph", "userType: $userType")
    Log.d("NavGraph", "is LoggedIn: $isLoggedIn")
    NavHost(
        navController = navController, startDestination =
        if (!isLoggedIn) {
            Screen.Landing.route
        } else {
            when (userType) {
                0 -> Screen.MuridDashboard.route
                1 -> Screen.GuruDashboard.route
                else -> Screen.AdminDashboard.route
            }
        }
    ) {
        composable(route = Screen.Landing.route) {
            LandingScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screen.TermsCondition.route) {
            TermsConditionScreen(navController)
        }

        //  Auth
        composable(route = Screen.Role.route) {
            RoleScreen(navController)
        }
        composable(route = Screen.Register.route,
            arguments = listOf(
                navArgument(KEY_USER_TYPE) {
                    type = NavType.IntType
                }
            )) {
            val id = it.arguments!!.getInt(KEY_USER_TYPE)
            RegisterScreen(navController, id)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }

        //  Murid
        composable(route = Screen.MuridDashboard.route) {
            MuridDashboardScreen(navController)
        }
        composable(route = Screen.Introduction.route) {
            IntroductionScreen(navController)
        }
        composable(route = Screen.MuridMateri.route) {
            MuridMateriScreen(navController)
        }
        composable(route = Screen.MuridDetailMateri.route) {
            MuridDetailMateriScreen(navController)
        }
        composable(route = Screen.MuridLatihan.route) {
            MuridLatihanScreen(navController)
        }
        composable(route = Screen.MuridDetailLatihan.route) {
            MuridDetailLatihanScreen(navController)
        }
        composable(route = Screen.Reaksi.route) {
            ReaksiScreen(navController)
        }
        composable(route = Screen.Nilai.route) {
            NilaiScreen(navController)
        }
        composable(route = Screen.CekJawaban.route) {
            CekJawabanScreen(navController)
        }

        //  Guru
        composable(route = Screen.GuruDashboard.route) {
            GuruDashboardScreen(navController)
        }
        composable(route = Screen.AddMateri.route) {
            AddMateriScreen(navController)
        }
        composable(route = Screen.AddLatihan.route) {
            AddLatihanScreen(navController)
        }
        composable(route = Screen.GuruMateri.route) {
            GuruMateriScreen(navController)
        }
        composable(route = Screen.GuruLatihan.route) {
            GuruLatihanScreen(navController)
        }
        composable(route = Screen.GuruDetailMateri.route) {
            DetailMateriScreen(navController)
        }
        composable(route = Screen.GuruDetailLatihan.route,
            arguments = listOf(
                navArgument(KEY_EXERCISE_ID) {
                    type = NavType.IntType
                }
            )) {
            val exerciseId = it.arguments!!.getInt(KEY_EXERCISE_ID)
            DetailLatihanScreen(navController, exerciseId)
        }

        // Admin
        composable(route = Screen.AdminDashboard.route) {
            AdminDashboardScreen(navController)
        }
        composable(route = Screen.CheckUser.route,
            arguments = listOf(
                navArgument(KEY_USER_EMAIL) {
                    type = NavType.StringType
                }
            )) {
            val email = it.arguments!!.getString(KEY_USER_EMAIL)
            CheckUserScreen(navController, email!!)
        }
        composable(route = Screen.CheckFile.route) {
            CheckFileScreen(navController)
        }
        composable(route = Screen.UsersInfo.route,
            arguments = listOf(
                navArgument(KEY_USER_ID) {
                    type = NavType.IntType
                }
            )) {
            val userId = it.arguments!!.getInt(KEY_USER_ID)
            UsersInfoScreen(navController, userId)
        }
        composable(route = Screen.FileInfo.route) {
            FileInfoScreen(navController)
        }
        composable(route = Screen.ManageIntroContent.route) {
            ManageContentScreen(navController)
        }
        composable(route = Screen.UpdateIntroContent.route) {
            UpdateIntroContentScreen(navController)
        }
        composable(route = Screen.AddSoal.route,
            arguments = listOf(
            navArgument(KEY_EXERCISE_ID) {
                type = NavType.IntType
            }
        )) {
            val exerciseId = it.arguments!!.getInt(KEY_EXERCISE_ID)
            AddSoalScreen(navController, exerciseId = exerciseId)
        }
    }
}