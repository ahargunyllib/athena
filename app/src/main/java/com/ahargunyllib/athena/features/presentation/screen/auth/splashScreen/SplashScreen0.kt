package com.ahargunyllib.athena.features.presentation.screen.auth.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Athena Logo",
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}