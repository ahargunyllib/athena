package com.ahargunyllib.athena.features.presentation.designSystem.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.ahargunyllib.athena.features.presentation.designSystem.Main
import com.ahargunyllib.athena.features.presentation.designSystem.Typography

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DateDialog(
    showDialog: MutableState<Boolean>,
    dateOfBirth: DatePickerState
) {
    DatePickerDialog(
        onDismissRequest = {
            showDialog.value = false
        },
        confirmButton = {
            Button(
                onClick = { showDialog.value = false },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Main,
                    containerColor = Color.Transparent,
                )
            ) {
                Text(
                    text = "OK",
                    style = Typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { showDialog.value = false },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Main,
                    containerColor = Color.Transparent,
                )
            ) {
                Text(
                    text = "Cancel",
                    style = Typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
    ) {
        DatePicker(
            state = dateOfBirth,
            showModeToggle = false,
            title = null,
            headline = null
        )
    }
}