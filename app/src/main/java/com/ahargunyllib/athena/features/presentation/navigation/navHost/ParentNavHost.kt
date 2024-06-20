package com.ahargunyllib.athena.features.presentation.navigation.navHost

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.chat.chatRoom.ChatRoomScreen
import com.ahargunyllib.athena.features.presentation.screen.home.HomeViewModel
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileScreen
import com.ahargunyllib.athena.features.presentation.screen.profile.editCredentials.EditCredentialsScreen
import com.ahargunyllib.athena.features.presentation.screen.profile.editProfile.EditProfileScreen

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
    ) {
        composable(ParentNavObj.LoginNavObj.route) {
            AuthNavHost(parentNavController)
        }

        composable(ParentNavObj.BottomNavObj.route) {
            BottomNavHost(parentNavController)
        }

        composable(
            "${ParentNavObj.ChatRoomNavObj.route}/{chatRoomId}/{friendId}",
            arguments = listOf(
                navArgument("chatRoomId") { type = NavType.StringType },
                navArgument("friendId") { type = NavType.StringType },
            )
        ) {
            ChatRoomScreen(parentNavController, it.arguments?.getString("chatRoomId") ?: "", it.arguments?.getString("friendId") ?: "")
        }

        composable(ParentNavObj.ProfileNavObj.route) {
            ProfileScreen(parentNavController)
        }

        composable(ParentNavObj.EditProfileNavObj.route) {
            EditProfileScreen(parentNavController)
        }

        composable(ParentNavObj.EditCredentialsNavObj.route) {
            EditCredentialsScreen(parentNavController)
        }
    }
}