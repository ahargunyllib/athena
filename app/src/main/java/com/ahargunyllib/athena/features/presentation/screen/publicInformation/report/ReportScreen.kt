package com.ahargunyllib.athena.features.presentation.screen.publicInformation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahargunyllib.athena.features.domain.model.CreateReportModel
import com.ahargunyllib.athena.features.presentation.designSystem.Black
import com.ahargunyllib.athena.features.presentation.designSystem.Gray
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.MainLight
import com.ahargunyllib.athena.features.presentation.designSystem.Typography
import com.ahargunyllib.athena.features.presentation.navigation.navObject.ParentNavObj

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    parentController: NavController,
    publicInformationId: String,
) {
    val reportViewModel: ReportViewModel = hiltViewModel()
    val reportState = reportViewModel.reportState.collectAsState()

    val reason = remember { mutableStateOf("") }
    val confirmation = remember { mutableStateOf("") }

    LaunchedEffect(reportState.value.data != null) {
        if (reportState.value.data != null) {
            parentController.navigate("${ParentNavObj.PostNavObj.route}/$publicInformationId")
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
                        "Report Post",
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { parentController.navigate("${ParentNavObj.PostNavObj.route}/$publicInformationId") },
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
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Reasons",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = reason.value,
                onValueChange = { reason.value = it },
                textStyle = Typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                decorationBox = { textField ->
                    Row {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (reason.value.isEmpty()) {
                                Text(
                                    text = "Write here",
                                    style = Typography.bodyLarge,
                                    color = Gray
                                )
                            }
                            textField()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Write \"I wrote this because the post seems to be misleading\"",
                color = Gray,
                style = Typography.bodySmall,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = confirmation.value,
                onValueChange = { confirmation.value = it },
                textStyle = Typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                decorationBox = { textField ->
                    Row {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (confirmation.value.isEmpty()) {
                                Text(
                                    text = "Write here",
                                    style = Typography.bodyLarge,
                                    color = Gray
                                )
                            }
                            textField()
                        }
                    }
                }
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
                    if (confirmation.value == "I wrote this because the post seems to be misleading") {
                        reportViewModel.createReport(
                            CreateReportModel(
                                reason = reason.value
                            ),
                            publicInformationId
                        )
                    } else {
                        // Show error dialog
                    }
                },
            ) {
                Text(
                    text = "Report",
                    color = Color.White,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}