package com.coppel.technicaltest.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private var _loginUiState = mutableStateOf(LoginUiState())
    val loginUiState: State<LoginUiState> get() = _loginUiState

    fun onUserChanged(newUser: String) {
        _loginUiState.value = _loginUiState.value.copy(user = newUser)
    }

    fun onPasswordChanged(newPassword: String) {
        _loginUiState.value = _loginUiState.value.copy(password = newPassword)
    }

    fun onBiometricChanged(enabled: Boolean) {
        _loginUiState.value = _loginUiState.value.copy(isBiometricEnabled = enabled)
    }

}