package com.coppel.technicaltest.ui.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coppel.technicaltest.ui.screens.home.HomeScreen
import com.coppel.technicaltest.ui.screens.home.HomeViewModel
import com.coppel.technicaltest.utils.GoogleAuthUiClient
import com.coppel.technicaltest.ui.screens.signIn.SignInScreen
import com.coppel.technicaltest.ui.screens.signIn.SignInViewModel
import com.coppel.technicaltest.ui.screens.signUp.SignUpScreen
import com.coppel.technicaltest.ui.screens.signUp.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper(googleAuthUiClient: GoogleAuthUiClient) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SignIn) {

        composable<SignIn> {
            val signInViewModel = hiltViewModel<SignInViewModel>()
            val lifecycleOwner = LocalLifecycleOwner.current
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.getSignInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            signInViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )
            SignInScreen(
                signInViewModel = signInViewModel,
                onGoogleSignIn = {
                    lifecycleOwner.lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                navigateToSignUp = {
                    navController.navigate(SignUp) {
                        popUpTo(SignIn) { inclusive = false }
                    }
                },
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(SignIn) { inclusive = true }
                    }
                }
            )
        }

        composable<SignUp> {
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(signUpViewModel) {
                navController.navigate(SignIn) {
                    popUpTo(SignIn) { inclusive = true }
                }
            }
        }

        composable<Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel) {
                navController.navigate(SignIn) {
                    popUpTo<SignIn> { inclusive = true }
                }
            }
        }

    }

}