package org.d3ifcool.virtualab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3ifcool.virtualab.ui.screen.about.AboutScreen
import org.d3ifcool.virtualab.ui.screen.murid.dashboard.MuridDashboardScreen
import org.d3ifcool.virtualab.ui.screen.murid.introduction.IntroductionScreen
import org.d3ifcool.virtualab.ui.screen.landing.LandingScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.LatihanScreen
import org.d3ifcool.virtualab.ui.screen.auth.RegisterScreen
import org.d3ifcool.virtualab.ui.screen.auth.LoginScreen
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.GuruLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.AddMateriScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridDetailMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.nilai.NilaiScreen
import org.d3ifcool.virtualab.ui.screen.profile.ProfileScreen
import org.d3ifcool.virtualab.ui.screen.murid.reaksi.ReaksiScreen
import org.d3ifcool.virtualab.ui.screen.role.RoleScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(route = Screen.Landing.route) {
            LandingScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }

        //  Auth
        composable(route = Screen.Role.route) {
            RoleScreen(navController)
        }
        composable(route = Screen.Register.route,
            arguments = listOf(
                navArgument(KEY_ID_USER) {
                    type = NavType.LongType
                }
            )) {
            val id = it.arguments?.getLong(KEY_ID_USER)
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
        composable(route = Screen.Latihan.route) {
            LatihanScreen(navController)
        }
        composable(route = Screen.Reaksi.route) {
            ReaksiScreen(navController)
        }
        composable(route = Screen.Nilai.route) {
            NilaiScreen(navController)
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
    }
}