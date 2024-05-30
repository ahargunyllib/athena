package com.ahargunyllib.athena.features.presentation.screen.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj

@Composable
fun LoginScreen(
    authController: NavController = rememberNavController(),
    parentController: NavController = rememberNavController()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { it ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    authController.navigate(AuthNavObj.RegisterNavObj.route)
                },
            ) {
                Text(text = "Go to Register")
            }

            Button(
                onClick = {
                    parentController.navigate(ParentNavObj.BottomNavObj.route)
                },
            ) {
                Text(text = "Go to Home")
            }
        }
    }
}