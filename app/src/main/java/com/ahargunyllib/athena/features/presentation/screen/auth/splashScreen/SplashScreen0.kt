package com.ahargunyllib.athena.features.presentation.screen.auth.splashScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import kotlinx.coroutines.delay


@Composable
fun SplashScreen0(
    authController: NavController = rememberNavController(),
) {
    LaunchedEffect(Unit) {
        delay(2500)
        authController.navigate(AuthNavObj.Splash1NavObj.route)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { it ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Splash Screen 0 ")
        }
    }
}