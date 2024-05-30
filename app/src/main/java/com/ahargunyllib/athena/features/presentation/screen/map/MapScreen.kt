package com.ahargunyllib.athena.features.presentation.screen.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    parentController: NavController = rememberNavController(),
    bottomNavController: NavController = rememberNavController(),
    onChangeScreen: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { it ->
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                mapType = MapType.NORMAL,
                isTrafficEnabled = true
            )
        ) {

        }
    }
}