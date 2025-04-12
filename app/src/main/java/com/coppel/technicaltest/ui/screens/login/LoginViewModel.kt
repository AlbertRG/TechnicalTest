package com.coppel.technicaltest.ui.screens.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.technicaltest.domain.usercase.GetBiometricCheckUseCase
import com.coppel.technicaltest.domain.usercase.GetUserUseCase
import com.coppel.technicaltest.domain.usercase.SetBiometricCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getBiometricCheckUseCase: GetBiometricCheckUseCase,
    private val setBiometricCheckUseCase: SetBiometricCheckUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private var _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> get() = _loginState

    init {
        checkBiometricPreference()
    }

    private fun checkBiometricPreference() {
        viewModelScope.launch {
            val isBiometricEnabled = getBiometricCheckUseCase().first()
            _loginState.value = _loginState.value.copy(isBiometricEnabled = isBiometricEnabled)
        }
    }

    fun validateCredentials(){
        val userInput = _loginState.value.user.trim()
        val passwordInput = _loginState.value.password

        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            _loginState.value = _loginState.value.copy(
                passError = true,
                passSuppText = "Fields cannot be empty"
            )
            return
        }

        viewModelScope.launch {
            try {
                getUserUseCase(userInput).collect { user ->
                    if (user.password == passwordInput) {
                        _loginState.value = _loginState.value.copy(
                            loginSuccess = true,
                            passError = false,
                            passSuppText = ""
                        )
                        Log.d("LoginViewModel", "Login successful for user: ${user.user}")
                    } else {
                        _loginState.value = _loginState.value.copy(
                            passError = true,
                            passSuppText = "Incorrect password"
                        )
                    }
                }
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(
                    passError = true,
                    passSuppText = "User not found"
                )
                Log.e("LoginViewModel", "Login failed", e)
            }
        }
    }

    fun onLoginSuccessChanged(success: Boolean) {
        _loginState.value = _loginState.value.copy(loginSuccess = success)
    }

    fun onUserChanged(newUser: String) {
        _loginState.value = _loginState.value.copy(user = newUser)
    }

    fun onPasswordChanged(newPassword: String) {
        _loginState.value = _loginState.value.copy(
            password = newPassword,
            passError = false,
            passSuppText = ""
        )
    }

    fun onBiometricChanged(enabled: Boolean) {
        _loginState.value = _loginState.value.copy(isBiometricEnabled = enabled)
        viewModelScope.launch {
            setBiometricCheckUseCase(enabled)
        }
    }

    fun onPasswordVisibilityChanged() {
        _loginState.value =
            _loginState.value.copy(passVisibility = !_loginState.value.passVisibility)
    }

}