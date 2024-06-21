package com.ahargunyllib.athena.features.presentation.navigation.navHost

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.MainDarkActive
import com.ahargunyllib.athena.features.presentation.designSystem.MainLightActive
import com.ahargunyllib.athena.features.presentation.navigation.navObject.BottomNavObj
import com.ahargunyllib.athena.features.presentation.screen.chat.ChatScreen
import com.ahargunyllib.athena.features.presentation.screen.comingSoon.ComingSoonScreen
import com.ahargunyllib.athena.features.presentation.screen.map.MapScreen

@SuppressLint("MissingPermission")
@Composable
fun BottomNavHost(
    parentController: NavController = rememberNavController()
) {
    val bottomNavController = rememberNavController()
    val selected = remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MainLightActive
            ) {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainDarkActive,
                        unselectedIconColor = Gray
                    ),
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
                    selected = selected.intValue == 0,
                    onClick = {
                        bottomNavController.navigate(BottomNavObj.HomeNavObj.route)
                        selected.intValue = 0
                    },
                )
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainDarkActive,
                        unselectedIconColor = Gray
                    ),
                    icon = {
                        Icon(
                            Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Chat"
                        )
                    },
                    selected = selected.intValue == 1,
                    onClick = {
                        bottomNavController.navigate(BottomNavObj.ChatNavObj.route)
                        selected.intValue = 1
                    },
                )

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainDarkActive,
                        unselectedIconColor = Gray
                    ),
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.InsertDriveFile,
                            contentDescription = "Memory"
                        )
                    },
                    selected = selected.intValue == 2,
                    onClick = {
                        bottomNavController.navigate(BottomNavObj.MemoryNavObj.route)
                        selected.intValue = 2
                    },
                )

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainDarkActive,
                        unselectedIconColor = Gray
                    ),
                    icon = {
                        Icon(
                            Icons.Outlined.Lightbulb,
                            contentDescription = "Education"
                        )
                    },
                    selected = selected.intValue == 3,
                    onClick = {
                        bottomNavController.navigate(BottomNavObj.EducationNavObj.route)
                        selected.intValue = 3
                    },
                )
            }
        }
    ) { _ ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavObj.HomeNavObj.route,
        ) {
            composable(BottomNavObj.HomeNavObj.route) {
                MapScreen(
                    parentController = parentController
                )
            }

            composable(BottomNavObj.ChatNavObj.route) {
                ChatScreen(
                    parentController = parentController
                )
            }

            composable(BottomNavObj.MemoryNavObj.route) {
                ComingSoonScreen(
                )
            }

            composable(BottomNavObj.EducationNavObj.route) {
                ComingSoonScreen(
                )
            }
        }
    }
}