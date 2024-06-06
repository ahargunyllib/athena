package com.ahargunyllib.athena.features.presentation.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.presentation.designSystem.fontFamily
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj

@Composable
fun ProfileScreen(
    parentController: NavController = rememberNavController(),
    bottomNavController: NavController = rememberNavController(),
    onChangeScreen: (Int) -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { it ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Profile Screen", fontFamily = fontFamily)
            Text(text = "This is the profile screen", fontFamily = FontFamily.SansSerif)
            Button(onClick = {
                profileViewModel.logout()
                parentController.navigate(ParentNavObj.LoginNavObj.route)
            }) {
                Text(text = "Logout", fontFamily = fontFamily)
            }
        }
    }
}