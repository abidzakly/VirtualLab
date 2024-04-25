package com.vlab2024.virtuallab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vlab2024.virtuallab.ui.screen.LandingScreen
import com.vlab2024.virtuallab.ui.screen.RegisterScreen
import com.vlab2024.virtuallab.ui.screen.LoginScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(route = Screen.Landing.route) {
            LandingScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
    }
}