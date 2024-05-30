package com.ahargunyllib.athena.features.presentation.navigation.navObject

sealed class BottomNavObj (val route: String) {
    data object HomeNavObj : BottomNavObj("home")
    data object ProfileNavObj : BottomNavObj("profile")
}