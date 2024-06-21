package com.ahargunyllib.athena.features.presentation.screen.profile.editCredentials

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.domain.model.CredentialsModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.widget.ErrorDialog
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EditCredentialsScreen(
    parentController: NavController = rememberNavController()
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userState = profileViewModel.userState.collectAsState()

    val editCredentialsViewModel: EditCredentialsViewModel = hiltViewModel()
    val editCredentialsState = editCredentialsViewModel.editCredentialsState.collectAsState()

    val showInfoDialog = remember { mutableStateOf(false) }

    val currentEmail = userState.value.data?.email ?: ""
    val newEmail = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    if (editCredentialsState.value.isLoading) {
        // Show loading
    } else {
        if (editCredentialsState.value.data == null) {
            showInfoDialog.value = true
        } else {
            parentController.navigate(ParentNavObj.ProfileNavObj.route)
        }

    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MainLight),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Credentials",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { parentController.navigate(ParentNavObj.ProfileNavObj.route) },
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
        if (userState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .size(32.dp),
                color = Main
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainLight)
                    .padding(horizontal = 20.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "Current Email",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = currentEmail,
                    onValueChange = {  },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                    enabled = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Email",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = newEmail.value,
                    onValueChange = { newEmail.value = it },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Password",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicSecureTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Confirm Password",
                    color = Gray,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicSecureTextField(
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = (12.5).dp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Main,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.5f),
                        disabledContainerColor = Main.copy(alpha = 0.5f),
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        editCredentialsViewModel.updateUser(
                            CredentialsModel(
                                email = newEmail.value,
                                password = password.value,
                                confirmPassword = confirmPassword.value
                            )
                        )
                    },
                ) {
                    Text(
                        text = "Proceed",
                        color = Color.White,
                        style = Typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (showInfoDialog.value) {
                ErrorDialog(editCredentialsState, showInfoDialog)
            }
        }
    }
}
