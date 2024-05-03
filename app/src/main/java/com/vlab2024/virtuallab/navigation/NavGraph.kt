package com.vlab2024.virtuallab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vlab2024.virtuallab.ui.screen.dashboard.DashboardScreen
import com.vlab2024.virtuallab.ui.screen.landing.LandingScreen
import com.vlab2024.virtuallab.ui.screen.register.RegisterScreen
import com.vlab2024.virtuallab.ui.screen.login.LoginScreen
import com.vlab2024.virtuallab.ui.screen.role.RoleScreen

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
    }
}