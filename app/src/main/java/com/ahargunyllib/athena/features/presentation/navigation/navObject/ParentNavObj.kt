package com.ahargunyllib.athena.features.presentation.navigation.navObject

sealed class ParentNavObj (val route: String) {
    data object LoginNavObj : ParentNavObj("login")
    data object BottomNavObj : ParentNavObj("bottom")
}