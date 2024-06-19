package com.ahargunyllib.athena.features.presentation.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahargunyllib.athena.R
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Border
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Information500
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj
import com.ahargunyllib.athena.features.presentation.screen.auth.register.RegisterState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    authController: NavController = rememberNavController(),
    parentController: NavController = rememberNavController()
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val showInfoDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState = loginViewModel.loginState.collectAsState()

    if (loginState.value.isLoading) {
        // Show loading
    } else {
        showInfoDialog.value = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 56.dp),
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
                text = "Sign in to your Account",
                color = Black,
                style = Typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Enter your email and password to log in",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(32.dp))
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
                text = "Forgot Password?",
                color = Information500,
                style = Typography.bodySmall,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                colors = ButtonColors(
                    containerColor = Main,
                    contentColor = Color.White,
                    disabledContentColor = Color.White.copy(alpha = 0.5f),
                    disabledContainerColor = Main.copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    loginViewModel.login(
                        context,
                        LoginModel(
                            email = email.value,
                            password = password.value,
                        )
                    )
                },
            ) {
                Text(
                    text = "Log In",
                    color = Color.White,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    text = "Or",
                    color = Gray,
                    style = Typography.labelMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Border, RoundedCornerShape(10.dp))
                    .height(48.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Black,
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    // Show toast
                    Toast.makeText(context, "This feature is currently disabled", Toast.LENGTH_SHORT).show()
                },
            ) {
                Image(painterResource(id = R.drawable.google), contentDescription = "Google")
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Continue with Google",
                    color = Black,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Gray,
                    style = Typography.labelMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "Sign Up",
                    color = Information500,
                    style = Typography.labelMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        authController.navigate(AuthNavObj.RegisterNavObj.route)
                    }
                )
            }

            if (showInfoDialog.value) {
                InfoDialog(
                    loginState,
                    showInfoDialog,
                    parentController
                )
            }
        }
    }
}

@Composable
private fun InfoDialog(
    loginState: State<LoginState>,
    showInfoDialog: MutableState<Boolean>,
    parentController: NavController
) {
    Dialog(onDismissRequest = {
        showInfoDialog.value = false
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 8.dp,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loginState.value.message.contains("Success")) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Success",
                        tint = Main,
                        modifier = Modifier.size(80.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "SUCCESS!",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Main
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Login successful.",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = loginState.value.message,
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            showInfoDialog.value = false
                            parentController.navigate(ParentNavObj.BottomNavObj.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Main
                        )
                    ) {
                        Text(
                            "Continue",
                            style = Typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(80.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "ERROR!",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Danger
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "An error occurred while login with your account. Please try again.",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = loginState.value.message,
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            showInfoDialog.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Danger
                        )
                    ) {
                        Text(
                            "Retry",
                            style = Typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

        }
    }
}
