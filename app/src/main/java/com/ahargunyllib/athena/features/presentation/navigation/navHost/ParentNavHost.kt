package com.ahargunyllib.athena.features.presentation.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.auth.login.LoginScreen

@Composable
fun ParentNavHost() {
    val parentNavController = rememberNavController()

    NavHost(
        navController = parentNavController,
        startDestination = if (true) ParentNavObj.LoginNavObj.route else ParentNavObj.BottomNavObj.route,
    ){
        composable(ParentNavObj.LoginNavObj.route){
            AuthNavHost(parentNavController)
        }

        composable(ParentNavObj.BottomNavObj.route){
            BottomNavHost(parentNavController)
        }
    }
}