package com.ahargunyllib.athena.features.presentation.navigation.navHost

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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.ahargunyllib.athena.features.presentation.navigation.navObject.BottomNavObj
import com.ahargunyllib.athena.features.presentation.screen.home.HomeScreen
import com.ahargunyllib.athena.features.presentation.screen.map.MapScreen
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileScreen

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
            BottomNavBar(
                bottomController = bottomNavController,
                selected = selected.value
            ) {
                selected.value = it
            }
        }
    ) { it ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavObj.HomeNavObj.route
        ) {
            composable(BottomNavObj.HomeNavObj.route) {
                HomeScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }

            composable(BottomNavObj.ProfileNavObj.route) {
                ProfileScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }

            composable(BottomNavObj.MapNavObj.route) {
                MapScreen(
                    parentController = parentController,
                    bottomNavController = bottomNavController
                ) {
                    selected.value = it
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    bottomController: NavController = rememberNavController(),
    selected: Int = 0,
    onSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Outlined.Home,
                selected = selected == 0,
                onClick = {
                    bottomController.navigate(BottomNavObj.HomeNavObj.route)
                    onSelected(0)
                },
                text = "Home"
            )
            BottomNavItem(
                icon = Icons.Outlined.Person,
                selected = selected == 1,
                onClick = {
                    bottomController.navigate(BottomNavObj.ProfileNavObj.route)
                    onSelected(1)
                },
                text = "Profile"
            )
            BottomNavItem(
                icon = Icons.Outlined.Map,
                selected = selected == 2,
                onClick = {
                    bottomController.navigate(BottomNavObj.MapNavObj.route)
                    onSelected(2)
                },
                text = "Map"
            )
        }
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, selected: Boolean, onClick: () -> Unit, text: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier
                .background(
                    color = if (selected) {
                        Color.LightGray
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (selected) {
                            Color.LightGray
                        } else {
                            Color.Transparent
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = Color.Black,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    }
}