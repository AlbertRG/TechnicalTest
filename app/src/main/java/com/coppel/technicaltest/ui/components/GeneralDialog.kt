package com.coppel.technicaltest.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun GeneralDialog(
    icon: Int,
    iconDescription: String? = null,
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String = "Confirm",
    onConfirmation: () -> Unit,
    hasDismissButton: Boolean = true,
    dismissButtonText: String = "Cancel",
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = confirmButtonText,
                    color = Color(0xFF665AFF)
                )
            }
        },
        dismissButton = {
            if (hasDismissButton) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = dismissButtonText,
                        color = Color.Red
                    )
                }
            }
        },
        icon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = iconDescription,
                tint = Color(0xFF665AFF)
            )
        },
        title = {
            Text(
                text = dialogTitle,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Text(
                text = dialogText,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        },
        containerColor = Color.White
    )
}