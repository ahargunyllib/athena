package com.ahargunyllib.athena.features.presentation.navigation.navObject

sealed class AuthNavObj(val route: String) {
    data object Splash0NavObj : AuthNavObj("splash0")
    data object Splash1NavObj : AuthNavObj("splash1")
    data object LoginNavObj : AuthNavObj("login")
    data object RegisterNavObj : AuthNavObj("register")

}