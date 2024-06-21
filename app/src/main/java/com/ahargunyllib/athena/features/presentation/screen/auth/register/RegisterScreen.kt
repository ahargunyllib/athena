package com.ahargunyllib.athena.features.presentation.screen.auth.register

import android.text.format.DateFormat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Information500
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.widget.DateDialog
import com.ahargunyllib.athena.features.presentation.widget.InfoDialog
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    authController: NavController = rememberNavController()
) {
    val fullName = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val dateOfBirth = rememberDatePickerState()
    val millisToLocalDate = dateOfBirth.selectedDateMillis?.let {
        DateFormat.format("dd/MM/yyyy", it)
    }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val showDateDialog = remember { mutableStateOf(false) }
    val showInfoDialog = remember { mutableStateOf(false) }

    val registerViewModel: RegisterViewModel = hiltViewModel()
    val registerState = registerViewModel.registerState.collectAsState()


    if (registerState.value.isLoading) {
        // Show loading
    } else {
        showInfoDialog.value = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 56.dp)
                .padding(paddingValues) // Correctly apply padding from Scaffold
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Black,
                modifier = Modifier
                    .size(24.dp)
                    .padding()
                    .clickable {
                        authController.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Sign Up",
                color = Black,
                style = Typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Create an account to continue!",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Full Name",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = fullName.value,
                onValueChange = { fullName.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Username",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = username.value,
                onValueChange = { username.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Email",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = email.value,
                onValueChange = { email.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Birth of Date",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    text = millisToLocalDate?.toString() ?: "Choose Date",
                    color = Typography.bodyMedium.color,
                    style = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.clickable {
                        showDateDialog.value = true
                    }
                )
                IconButton(
                    onClick = {
                        showDateDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Choose Date",
                        tint = Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Phone Number",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Password",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicSecureTextField(
                value = password.value,
                onValueChange = { password.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = (12.5).dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Confirm Password",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicSecureTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                textStyle = Typography.bodyMedium.merge(TextStyle(fontWeight = FontWeight.SemiBold)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
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
                    registerViewModel.register(
                        RegisterModel(
                            fullName = fullName.value,
                            username = username.value,
                            email = email.value,
                            dateOfBirth = Date(dateOfBirth.selectedDateMillis ?: 0).toInstant()
                                .toString(),
                            phoneNumber = phoneNumber.value,
                            password = password.value,
                            confirmPassword = confirmPassword.value
                        )
                    )
                },
            ) {
                Text(
                    text = "Register",
                    color = Color.White,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Gray,
                    style = Typography.labelMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "Login",
                    color = Information500,
                    style = Typography.labelMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        authController.navigate(AuthNavObj.LoginNavObj.route)
                    }
                )
            }

            if (showDateDialog.value) {
                DateDialog(showDateDialog, dateOfBirth)
            }

            if (showInfoDialog.value) {
                InfoDialog(registerState, showInfoDialog, authController)
            }
        }
    }
}
