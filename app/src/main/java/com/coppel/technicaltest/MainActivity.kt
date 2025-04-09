package com.coppel.technicaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.coppel.technicaltest.ui.navigation.NavigationWrapper
import com.coppel.technicaltest.ui.theme.TechnicalTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechnicalTestTheme {
                NavigationWrapper()
            }
        }
    }
}