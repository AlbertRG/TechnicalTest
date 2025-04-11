package com.coppel.technicaltest.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coppel.technicaltest.R
import com.coppel.technicaltest.ui.screens.home.HomeViewModel

@Composable
fun HomeError(
    homeViewModel: HomeViewModel
) {
    val homeState = homeViewModel.homeState.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFC7C4EE))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_error_24),
            contentDescription = "Error",
            modifier = Modifier
                .size(120.dp),
            colorFilter = ColorFilter.tint(Color(0xFF665AFF))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ops! Something went wrong",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF665AFF)
            )
        ) {
            Text(
                text = "Retry",
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeErrorPreview() {
    HomeError(homeViewModel = HomeViewModel())
}