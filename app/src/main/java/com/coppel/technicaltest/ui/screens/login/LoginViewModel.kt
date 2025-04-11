package com.coppel.technicaltest.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private var _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> get() = _loginState

    fun onUserChanged(newUser: String) {
        _loginState.value = _loginState.value.copy(user = newUser)
    }

    fun onPasswordChanged(newPassword: String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    fun onBiometricChanged(enabled: Boolean) {
        _loginState.value = _loginState.value.copy(isBiometricEnabled = enabled)
    }

    fun onPasswordVisibilityChanged() {
        _loginState.value = _loginState.value.copy(passVisibility = !_loginState.value.passVisibility)
    }

}