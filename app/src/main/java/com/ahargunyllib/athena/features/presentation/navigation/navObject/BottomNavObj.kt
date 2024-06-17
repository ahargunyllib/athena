package com.ahargunyllib.athena.features.presentation.navigation.navObject

sealed class BottomNavObj (val route: String) {
    data object HomeNavObj : BottomNavObj("home")
    data object ChatNavObj : BottomNavObj("chat")
    data object MemoryNavObj : BottomNavObj("memory")
    data object EducationNavObj : BottomNavObj("education")
}