package com.coppel.technicaltest.ui.screens.signUp

data class SignUpState(
    val isSignUpSuccessful: Boolean = false,
    val user: String = "",
    val password: String = "",
    var passVisibility: Boolean = false,
    val passSuppText: String = "",
    val passError: Boolean = false
)