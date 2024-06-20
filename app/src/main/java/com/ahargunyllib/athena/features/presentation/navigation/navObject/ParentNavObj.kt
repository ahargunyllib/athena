package com.ahargunyllib.athena.features.presentation.navigation.navObject

sealed class ParentNavObj (val route: String) {
    data object LoginNavObj : ParentNavObj("login")
    data object BottomNavObj : ParentNavObj("bottom")
    data object ChatRoomNavObj : ParentNavObj("chatroom")
    data object ProfileNavObj : ParentNavObj("profile")
    data object EditProfileNavObj : ParentNavObj("editprofile")
    data object EditCredentialsNavObj : ParentNavObj("editcredentials")
}