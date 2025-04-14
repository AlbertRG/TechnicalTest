package com.coppel.technicaltest.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.coppel.technicaltest.ui.screens.home.HomeViewModel

@Composable
fun FactDialog(
    homeViewModel: HomeViewModel,
) {
    val homeState = homeViewModel.homeState.value
    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                homeViewModel.initLocation()
            }
        }
    )
    Dialog(
        onDismissRequest = { homeViewModel.closeFactDialog() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                homeState.selectedFact?.let {
                    FactDialogText("ID: ", it.uid)
                    FactDialogText("Date Insert: ", it.dateInsert)
                    FactDialogText("Slug: ", it.slug)
                    FactDialogText("Columns: ", it.columns)
                    FactDialogText("Fact: ", it.fact)
                    FactDialogText("Organization: ", it.organization)
                    FactDialogText("Resource: ", it.resource)
                    FactDialogText("Url: ", it.url)
                    FactDialogText("Operations: ", it.operations)
                    FactDialogText("Dataset: ", it.dataset)
                    FactDialogText("Created At: ", homeViewModel.formatTimestamp(it.createdAt))
                }

                OutlinedTextField(
                    value = homeState.phoneNumber,
                    onValueChange = {
                        if (it.length <= 10) {
                            homeViewModel.onPhoneChanged(it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    label = {
                        Text(text = "Phone")
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Phone",
                            tint = Color(0xFF665AFF)
                        )
                    },
                    supportingText = {
                        Row {
                            Text(homeState.phoneSuppText)
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = "${homeState.phoneNumber.length}/10",
                                color = Color(0xFF665AFF)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFF665AFF),
                        unfocusedIndicatorColor = Color(0xFF665AFF),
                        focusedTextColor = Color(0xFF665AFF),
                        unfocusedTextColor = Color(0xFF665AFF),
                        focusedLabelColor = Color(0xFF665AFF),
                        unfocusedLabelColor = Color(0xFF665AFF),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            homeViewModel.closeFactDialog()
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(
                        onClick = {
                            if (homeViewModel.hasLocationPermission()) {
                                homeViewModel.initLocation()
                            } else {
                                requestLocationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    )
                                )
                            }
                        }
                    ) {
                        Text(
                            text = "Share",
                            color = Color(0xFF665AFF)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FactDialogText(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
