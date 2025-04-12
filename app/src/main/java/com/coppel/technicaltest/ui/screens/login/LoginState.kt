package com.coppel.technicaltest.ui.screens.login

data class LoginState(
    val loginSuccess: Boolean = false,
    val user: String = "",
    val password: String = "",
    var passVisibility: Boolean = false,
    val passSuppText: String = "",
    val passError: Boolean = false,
    val isBiometricEnabled: Boolean = false
)