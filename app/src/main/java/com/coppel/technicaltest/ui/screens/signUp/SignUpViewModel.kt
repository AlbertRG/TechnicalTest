package com.coppel.technicaltest.ui.screens.signUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coppel.technicaltest.domain.model.UserModel
import com.coppel.technicaltest.domain.usercase.GetUserUseCase
import com.coppel.technicaltest.domain.usercase.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private var _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> get() = _signUpState

    fun validateAndSignUp() {
        val username = _signUpState.value.user.trim()
        val password = _signUpState.value.password

        if (username.isEmpty() || password.isEmpty()) {
            _signUpState.value = _signUpState.value.copy(
                passError = true,
                passSuppText = "All fields must be filled"
            )
            return
        }

        if (password.length < 10) {
            _signUpState.value = _signUpState.value.copy(
                passError = true,
                passSuppText = "Password must be at least 10 characters"
            )
            return
        }

        viewModelScope.launch {
            try {
                getUserUseCase(username).collect {
                    _signUpState.value = _signUpState.value.copy(
                        passError = true,
                        passSuppText = "User already exists"
                    )
                }
            } catch (e: Exception) {
                if (e.message == "User not found") {
                    try {
                        val newUser = UserModel(
                            user = username,
                            password = password
                        )
                        insertUserUseCase(newUser)
                        _signUpState.value = _signUpState.value.copy(
                            isSignUpSuccessful = true,
                            passError = false,
                            passSuppText = ""
                        )
                    } catch (insertException: Exception) {
                        _signUpState.value = _signUpState.value.copy(
                            passError = true,
                            passSuppText = "Sign up failed"
                        )
                    }
                } else {
                    _signUpState.value = _signUpState.value.copy(
                        passError = true,
                        passSuppText = "Error checking user"
                    )
                }
            }
        }
    }

    fun onUserChanged(newUser: String) {
        _signUpState.value = _signUpState.value.copy(user = newUser)
    }

    fun onPasswordChanged(newPassword: String) {
        _signUpState.value = _signUpState.value.copy(
            password = newPassword,
            passError = false,
            passSuppText = ""
        )
    }

    fun onPasswordVisibilityChanged() {
        _signUpState.value =
            _signUpState.value.copy(passVisibility = !_signUpState.value.passVisibility)
    }

}