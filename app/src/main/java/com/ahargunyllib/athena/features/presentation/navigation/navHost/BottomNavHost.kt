package com.ahargunyllib.athena.features.presentation.navigation.navHost

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.ahargunyllib.athena.features.presentation.screen.home.HomeScreen
import com.ahargunyllib.athena.features.presentation.screen.map.MapScreen
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileScreen

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
    ) { it ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavObj.HomeNavObj.route
        ) {
            composable(BottomNavObj.HomeNavObj.route) {
                MapScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }

            composable(BottomNavObj.ChatNavObj.route) {
                ChatScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }

            composable(BottomNavObj.MemoryNavObj.route) {
                ComingSoonScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }

            composable(BottomNavObj.EducationNavObj.route) {
                ComingSoonScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }
        }
    }
}