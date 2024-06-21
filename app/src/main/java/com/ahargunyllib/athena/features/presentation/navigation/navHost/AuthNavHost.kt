package com.ahargunyllib.athena.features.presentation.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
import com.ahargunyllib.athena.features.presentation.screen.auth.login.LoginScreen
import com.ahargunyllib.athena.features.presentation.screen.auth.register.RegisterScreen
import com.ahargunyllib.athena.features.presentation.screen.auth.splashScreen.SplashScreen0
import com.ahargunyllib.athena.features.presentation.screen.auth.splashScreen.SplashScreen1

@Composable
fun AuthNavHost(
    parentController: NavController = rememberNavController()
) {
    val authController = rememberNavController()
    
    NavHost(
        navController = authController,
        startDestination = AuthNavObj.Splash0NavObj.route
    ) {
        composable(AuthNavObj.Splash0NavObj.route) {
            SplashScreen0(authController = authController)
        }

        composable(AuthNavObj.Splash1NavObj.route) {
            SplashScreen1(authController = authController)
        }

        composable(AuthNavObj.LoginNavObj.route) {
            LoginScreen(authController = authController, parentController = parentController)
        }

        composable(AuthNavObj.RegisterNavObj.route) {
            RegisterScreen(authController = authController)
        }
    }
}