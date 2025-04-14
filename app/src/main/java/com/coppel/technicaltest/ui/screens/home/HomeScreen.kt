package com.coppel.technicaltest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coppel.technicaltest.R
import com.coppel.technicaltest.ui.components.FactDialog
import com.coppel.technicaltest.ui.components.GeneralDialog
import com.coppel.technicaltest.ui.components.home.HomeError
import com.coppel.technicaltest.ui.components.home.HomeLoading
import com.coppel.technicaltest.ui.components.home.HomeSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navigateToLogin: () -> Unit
) {
    val homeState = homeViewModel.homeState.value
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF665AFF),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = "The Technical Test",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        homeViewModel.signOut(navigateToLogin)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Back to Login",
                            tint = Color.White
                        )
                    }
                }
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color(0xFFC7C4EE))
        ) {
            HorizontalDivider(thickness = 1.dp, color = Color.White)
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (homeState.homeState) {
                    HomeUiState.Loading -> HomeLoading()
                    HomeUiState.Success -> HomeSuccess(homeViewModel)
                    HomeUiState.Error -> HomeError(homeViewModel)
                }
            }
        }
    }
    if (homeState.selectedFact != null) {
        FactDialog(homeViewModel)
    }
    if (homeState.locationState == LocationState.InternetError) {
        GeneralDialog(
            icon = R.drawable.baseline_signal_wifi_connected_no_internet_4_24,
            dialogTitle = "Sin conexión",
            dialogText = "Por favor verifica tu conexión a internet",
            onConfirmation = { homeViewModel.closeErrorDialog() },
            hasDismissButton = false,
            onDismissRequest = { }
        )
    }

}