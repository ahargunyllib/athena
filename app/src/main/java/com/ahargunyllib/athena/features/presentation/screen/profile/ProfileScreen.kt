package com.ahargunyllib.athena.features.presentation.screen.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    parentController: NavController = rememberNavController()
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    Log.i("ProfileScreen", "ProfileScreen: ${userState.value}")

    val isSharingLocation = remember { mutableStateOf(userState.value.data?.isSharingLocation) }
    val isPauseAll = remember { mutableStateOf(userState.value.data?.isPauseAll) }
    val isShowNotification = remember { mutableStateOf(userState.value.data?.isShowNotification) }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight),
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(
                        onClick = { parentController.navigate(ParentNavObj.BottomNavObj.route) },
                        content = {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Black
                            )
                        })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MainLight),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainLight)
                .padding(horizontal = 20.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = userState.value.data?.imageUrl,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(92.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = ("@" + userState.value.data?.username),
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Button(
                onClick = {
                    parentController.navigate(ParentNavObj.EditProfileNavObj.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Main
                )
            ) {
                Text(text = "Edit Info", style = Typography.labelSmall)
            }
            Spacer(modifier = Modifier.size(48.dp))

            // TODO: General Settings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.People, contentDescription = "General Settings", tint = Gray)
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    "General Settings",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Gray
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        parentController.navigate(ParentNavObj.EditCredentialsNavObj.route)
                    }
            ) {
                Text(
                    "Change credentials",
                    style = Typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "Change your current E-mail and password",
                    style = Typography.labelSmall,
                    color = Gray
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.clickable {

                    }
                ) {
                    Text(
                        "Location Sharing",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "Share your current location",
                        style = Typography.labelSmall,
                        color = Gray
                    )
                }
                Switch(
                    checked = isSharingLocation.value ?: userState.value.data?.isSharingLocation
                    ?: false,
                    onCheckedChange = {
                        isSharingLocation.value = it
                        profileViewModel.updateIsSharingLocation(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Main,
                        uncheckedThumbColor = Gray,
                        uncheckedTrackColor = Gray.copy(alpha = 0.5f)
                    ),
                    thumbContent = {
                        if (isSharingLocation.value ?: userState.value.data?.isSharingLocation == true) {
                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = "Checked",
                                tint = Main
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Unchecked",
                                tint = Gray.copy(alpha = 0.5f)
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.size(36.dp))

            // TODO: Notification Settings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.People,
                    contentDescription = "Notification Settings",
                    tint = Gray
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    "Notification Settings",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Gray
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.clickable {

                    }
                ) {
                    Text(
                        "Pause All",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "Temporarily pause notifications",
                        style = Typography.labelSmall,
                        color = Gray
                    )
                }
                Switch(
                    checked = isPauseAll.value ?: userState.value.data?.isPauseAll ?: false,
                    onCheckedChange = {
                        isPauseAll.value = it
                        profileViewModel.updateIsPauseAll(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Main,
                        uncheckedThumbColor = Gray,
                        uncheckedTrackColor = Gray.copy(alpha = 0.5f)
                    ),
                    thumbContent = {
                        if (isPauseAll.value ?: userState.value.data?.isPauseAll == true) {
                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = "Checked",
                                tint = Main
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Unchecked",
                                tint = Gray.copy(alpha = 0.5f)
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.clickable {

                    }
                ) {
                    Text(
                        "Show Notification at all time",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        "Activated by default.",
                        style = Typography.labelSmall,
                        color = Gray
                    )
                }
                Switch(
                    checked = isShowNotification.value ?: userState.value.data?.isShowNotification
                    ?: false,
                    onCheckedChange = {
                        isShowNotification.value = it
                        profileViewModel.updateIsShowNotification(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Main,
                        uncheckedThumbColor = Gray,
                        uncheckedTrackColor = Gray.copy(alpha = 0.5f)
                    ),
                    thumbContent = {
                        if (isShowNotification.value ?: userState.value.data?.isShowNotification == true) {
                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = "Checked",
                                tint = Main
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Unchecked",
                                tint = Gray.copy(alpha = 0.5f)
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.size(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Logout",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Danger,
                    modifier = Modifier.clickable {
                        profileViewModel.logout()
                        parentController.navigate(ParentNavObj.LoginNavObj.route)
                    }
                )
            }
        }
    }
}