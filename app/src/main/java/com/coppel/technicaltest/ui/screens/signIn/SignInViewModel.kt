package com.coppel.technicaltest.ui.screens.signIn

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.technicaltest.domain.usercase.GetBiometricCheckUseCase
import com.coppel.technicaltest.domain.usercase.GetUserUseCase
import com.coppel.technicaltest.domain.usercase.SetBiometricCheckUseCase
import com.coppel.technicaltest.utils.LocationUtils
import com.coppel.technicaltest.utils.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getBiometricCheckUseCase: GetBiometricCheckUseCase,
    private val setBiometricCheckUseCase: SetBiometricCheckUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val locationUtils: LocationUtils
) : ViewModel() {

    private var _signInState = mutableStateOf(SignInState())
    val signInState: State<SignInState> get() = _signInState

    init {
        checkBiometricPreference()
    }

    fun hasLocationPermission(): Boolean {
        return locationUtils.hasLocationPermission()
    }

    private fun checkBiometricPreference() {
        viewModelScope.launch {
            val isBiometricEnabled = getBiometricCheckUseCase().first()
            _signInState.value = _signInState.value.copy(isBiometricEnabled = isBiometricEnabled)
        }
    }

    fun onSignInResult(result: SignInResult) {
        _signInState.value = _signInState.value.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )
    }

    fun validateCredentials() {
        val userInput = _signInState.value.user.trim()
        val passwordInput = _signInState.value.password

        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            _signInState.value = _signInState.value.copy(
                isPasswordError = true,
                passSuppText = "Fields cannot be empty"
            )
            return
        }

        viewModelScope.launch {
            try {
                getUserUseCase(userInput).collect { user ->
                    if (user.password == passwordInput) {
                        _signInState.value = _signInState.value.copy(
                            isSignInSuccessful = true,
                            isPasswordError = false,
                            passSuppText = ""
                        )
                        Log.d("LoginViewModel", "Login successful for user: ${user.user}")
                    } else {
                        _signInState.value = _signInState.value.copy(
                            isPasswordError = true,
                            passSuppText = "Incorrect password"
                        )
                    }
                }
            } catch (e: Exception) {
                _signInState.value = _signInState.value.copy(
                    isPasswordError = true,
                    passSuppText = "User not found"
                )
                Log.e("LoginViewModel", "Login failed", e)
            }
        }
    }

    fun onSignInSuccessChanged(success: Boolean) {
        _signInState.value = _signInState.value.copy(isSignInSuccessful = success)
    }

    fun onUserChanged(newUser: String) {
        _signInState.value = _signInState.value.copy(user = newUser)
    }

    fun onPasswordChanged(newPassword: String) {
        _signInState.value = _signInState.value.copy(
            password = newPassword,
            isPasswordError = false,
            passSuppText = ""
        )
    }

    fun onBiometricChanged(enabled: Boolean) {
        _signInState.value = _signInState.value.copy(isBiometricEnabled = enabled)
        viewModelScope.launch {
            setBiometricCheckUseCase(enabled)
        }
    }

    fun onPasswordVisibilityChanged() {
        _signInState.value =
            _signInState.value.copy(isPasswordVisible = !_signInState.value.isPasswordVisible)
    }

}