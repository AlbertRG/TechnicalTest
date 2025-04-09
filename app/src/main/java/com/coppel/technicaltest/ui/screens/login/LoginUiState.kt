package com.coppel.technicaltest.ui.screens.login

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val isBiometricEnabled: Boolean = false
)