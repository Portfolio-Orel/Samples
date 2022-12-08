package com.orels.samples.login.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orels.samples.R
import com.orels.samples.book_notes.common.Screen
import com.orels.samples.login.presentation.forgot_password.ForgotPasswordScreen
import com.orels.samples.login.presentation.main.MainScreen

sealed class LoginScreens(val route: String, @StringRes val resourceId: Int) {
    object Main : LoginScreens("main", R.string.main)
    object ForgotPassword : LoginScreens("forgot_password", R.string.forgot_password)

    fun withArgs(vararg args: String?): String =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg ?: ""}")
            }
        }
}

@Composable
fun LoginScreen() {
    val navHostController = rememberNavController()
    val navController = navHostController as NavController

    NavHost(
        navController = navHostController, startDestination = Screen.Main.route,
    ) {
        // Main Screen with spring animation
        composable(
            route = LoginScreens.Main.route,
        ) {
            MainScreen(navController = navController)
        }

        composable(
            route = LoginScreens.ForgotPassword.route,
        ) {
            ForgotPasswordScreen(navController = navController)
        }
    }
}