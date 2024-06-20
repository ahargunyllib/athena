package com.ahargunyllib.athena.features.presentation.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.ahargunyllib.athena.features.presentation.designSystem.Danger
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.AuthNavObj
import com.ahargunyllib.athena.features.presentation.screen.auth.register.RegisterState


@Composable
fun InfoDialog(
    registerState: State<RegisterState>,
    showInfoDialog: MutableState<Boolean>,
    authController: NavController
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
                if (registerState.value.message.contains("Success")) {
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
                        text = "Your account has been successfully registered.",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = registerState.value.message,
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            showInfoDialog.value = false
                            authController.navigate(AuthNavObj.LoginNavObj.route)
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
                        text = "An error occurred while registering your account. Please try again.",
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = registerState.value.message,
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
