package com.coppel.technicaltest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coppel.technicaltest.ui.screens.home.HomeScreen
import com.coppel.technicaltest.ui.screens.home.HomeViewModel
import com.coppel.technicaltest.ui.screens.login.LoginScreen
import com.coppel.technicaltest.ui.screens.login.LoginViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(loginViewModel) {
                navController.navigate(Home) {
                    popUpTo(Login) { inclusive = true }
                }
            }
        }

        composable<Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel) {
                navController.navigate(Login) {
                    popUpTo<Login> { inclusive = true }
                }
            }
        }

    }

}