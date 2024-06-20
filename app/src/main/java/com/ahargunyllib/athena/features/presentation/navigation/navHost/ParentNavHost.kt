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
import com.ahargunyllib.athena.features.presentation.screen.publicInformation.PublicInformationScreen
import com.ahargunyllib.athena.features.presentation.screen.publicInformation.locationPicker.LocationPickerScreen
import com.ahargunyllib.athena.features.presentation.screen.publicInformation.post.PostScreen
import com.ahargunyllib.athena.features.presentation.screen.publicInformation.report.ReportScreen

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
            ChatRoomScreen(
                parentNavController,
                it.arguments?.getString("chatRoomId") ?: "",
                it.arguments?.getString("friendId") ?: ""
            )
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

        composable(
            "${ParentNavObj.PublicInformationNavObj.route}?latitude={latitude}&longitude={longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.FloatType },
                navArgument("longitude") { type = NavType.FloatType },
            )
        ) {
            PublicInformationScreen(
                parentNavController,
                it.arguments?.getFloat("latitude") ?: 0f,
                it.arguments?.getFloat("longitude") ?: 0f
            )
        }

        composable(
            "${ParentNavObj.LocationPickerNavObj.route}?latitude={latitude}&longitude={longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.FloatType },
                navArgument("longitude") { type = NavType.FloatType },
            )
        ) {
            LocationPickerScreen(
                parentNavController,
                it.arguments?.getFloat("latitude") ?: 0f,
                it.arguments?.getFloat("longitude") ?: 0f
            )
        }

        composable(
            "${ParentNavObj.ReportNavObj.route}/{publicInformationId}",
            arguments = listOf(navArgument("publicInformationId") { type = NavType.StringType })
        ) {
            ReportScreen(parentNavController, it.arguments?.getString("publicInformationId") ?: "")
        }

        composable(
            "${ParentNavObj.PostNavObj.route}/{publicInformationId}",
            arguments = listOf(navArgument("publicInformationId") { type = NavType.StringType })
        ) {
            PostScreen(parentNavController, it.arguments?.getString("publicInformationId") ?: "")
        }
    }
}