package com.ahargunyllib.athena.features.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.designSystem.fontFamily
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj

@Composable
fun ProfileScreen(
    parentController: NavController = rememberNavController(),
    bottomNavController: NavController = rememberNavController(),
    onChangeScreen: (Int) -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight)
            .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 92.dp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainLight)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.dummy_avatar),
                contentDescription = "avatar",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(92.dp)
                    .clickable { }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = ("@" + userState.value.data?.username) ?: "",
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Main
                )
            ) {
                Text(text = "Edit Info", style = Typography.labelSmall)
            }
            Spacer(modifier = Modifier.size(48.dp))

            // TODO: General Settings
            Spacer(modifier = Modifier.size(36.dp))

            // TODO: Notification Settings
            Spacer(modifier = Modifier.size(48.dp))

            TextButton(
                onClick = {
                    profileViewModel.logout()
                    parentController.navigate(ParentNavObj.LoginNavObj.route)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Danger),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(text = "Logout", style = Typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}