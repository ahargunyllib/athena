package com.ahargunyllib.athena.features.presentation.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.designSystem.fontFamily

@Composable
fun ProfileScreen(
    parentController: NavController = rememberNavController(),
    bottomNavController: NavController = rememberNavController(),
    onChangeScreen: (Int) -> Unit
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
    ) { it ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Profile Screen", fontFamily = fontFamily)
            Text(text = "This is the profile screen", fontFamily = FontFamily.SansSerif)
        }
    }
}