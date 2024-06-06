package com.ahargunyllib.athena.features.presentation.navigation.navHost

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.home.HomeViewModel
import com.ahargunyllib.athena.features.presentation.screen.home.UserState

@Composable
 fun ParentNavHost() {
    val parentNavController = rememberNavController()

    // check token in user db
    val homeViewModel: HomeViewModel = hiltViewModel()
    val userState = homeViewModel.userState.collectAsState()
    val isUserLoggedIn = userState.value.data != null
    Log.i("ParentNavHost", "User: ${userState.value.data}")

    NavHost(
        navController = parentNavController,
        startDestination = if (isUserLoggedIn) ParentNavObj.BottomNavObj.route else ParentNavObj.LoginNavObj.route,
    ){
        composable(ParentNavObj.LoginNavObj.route){
            AuthNavHost(parentNavController)
        }

        composable(ParentNavObj.BottomNavObj.route){
            BottomNavHost(parentNavController)
        }
    }
}