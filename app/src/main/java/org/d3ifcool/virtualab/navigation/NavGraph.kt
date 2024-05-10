package org.d3ifcool.virtualab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3ifcool.virtualab.ui.screen.dashboard.DashboardScreen
import org.d3ifcool.virtualab.ui.screen.introduction.IntroductionScreen
import org.d3ifcool.virtualab.ui.screen.landing.LandingScreen
import org.d3ifcool.virtualab.ui.screen.latihan.LatihanScreen
import org.d3ifcool.virtualab.ui.screen.register.RegisterScreen
import org.d3ifcool.virtualab.ui.screen.login.LoginScreen
import org.d3ifcool.virtualab.ui.screen.materi.MateriScreen
import org.d3ifcool.virtualab.ui.screen.reaksi.ReaksiScreen
import org.d3ifcool.virtualab.ui.screen.role.RoleScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(route = Screen.Landing.route) {
            LandingScreen(navController)
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
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController)
        }
        composable(route = Screen.Role.route) {
            RoleScreen(navController)
        }
        composable(route = Screen.Introduction.route) {
            IntroductionScreen(navController)
        }
        composable(route = Screen.Materi.route) {
            MateriScreen(navController)
        }
        composable(route = Screen.Latihan.route) {
            LatihanScreen(navController)
        }
        composable(route = Screen.Reaksi.route) {
            ReaksiScreen(navController)
        }
    }
}