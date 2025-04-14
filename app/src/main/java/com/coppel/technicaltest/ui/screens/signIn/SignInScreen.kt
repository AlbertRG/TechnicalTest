package com.coppel.technicaltest.ui.screens.signIn

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.coppel.technicaltest.R
import com.coppel.technicaltest.ui.components.LoginBackground
import com.coppel.technicaltest.utils.BiometricAuthStatus
import com.coppel.technicaltest.utils.BiometricAuthenticator

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel,
    onGoogleSignIn: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val signInState = signInViewModel.signInState.value

    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                Log.d("LocationPermission", "Location permissions granted")
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!signInViewModel.hasLocationPermission()) {
            requestLocationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val biometricAuthenticator = BiometricAuthenticator(context)
    val authStatus = biometricAuthenticator.isBiometricAuthAvailable()
    if (authStatus == BiometricAuthStatus.READY && signInState.isBiometricEnabled && !signInState.isSignInSuccessful) {
        biometricAuthenticator.promptBiometricAuth(
            title = "Biometric Login",
            subTitle = "Please authenticate",
            negativeButtonText = "Cancel",
            fragmentActivity = activity!!,
            onSuccess = { result ->
                signInViewModel.onSignInSuccessChanged(true)
                Log.d("Biometric", "Authentication succeeded: $result")
            },
            onFailed = {
                Log.d("Biometric", "Authentication failed")
            },
            onError = { errorCode, errString ->
                Log.d("Biometric", "Error $errorCode: $errString")
            }
        )
    }

    LaunchedEffect(signInState.isSignInSuccessful) {
        if (signInState.isSignInSuccessful) {
            navigateToHome()
        }
    }

    LoginBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kotlin),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Text(
                text = "Sign In",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.White,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            OutlinedTextField(
                value = signInState.user,
                onValueChange = { signInViewModel.onUserChanged(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text("User")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "User",
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
            OutlinedTextField(
                value = signInState.password,
                onValueChange = {
                    if (it.length <= 10) {
                        signInViewModel.onPasswordChanged(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = {
                    Text("Password")
                },
                trailingIcon = {
                    val toggle = if (signInState.isPasswordVisible)
                        R.drawable.baseline_visibility_24
                    else
                        R.drawable.baseline_visibility_off_24
                    IconButton(
                        onClick = {
                            signInViewModel.onPasswordVisibilityChanged()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = toggle),
                            contentDescription = if (signInState.isPasswordVisible) "Hide password"
                            else "Show password",
                            tint = Color.White
                        )
                    }
                },
                supportingText = {
                    Row {
                        Text(signInState.passSuppText)
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "${signInState.password.length}/10",
                            color = Color.White
                        )
                    }
                },
                visualTransformation = if (signInState.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedSupportingTextColor = Color.White,
                    unfocusedSupportingTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = signInState.isBiometricEnabled,
                        onCheckedChange = { signInViewModel.onBiometricChanged(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF665AFF),
                            uncheckedColor = Color.White
                        )
                    )
                    Text(
                        text = "Fingerprint",
                        color = Color.White
                    )
                }
                Row {
                    Text(
                        text = "Sign Up",
                        modifier = Modifier
                            .clickable {
                                navigateToSignUp()
                            },
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Button(
                onClick = {
                    signInViewModel.validateCredentials()
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
                    text = "Sign in",
                    fontSize = 16.sp,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0f),
                                    Color.White.copy(alpha = 1f)
                                )
                            )
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Or continue with",
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 1f),
                                    Color.White.copy(alpha = 0f)
                                )
                            )
                        )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onGoogleSignIn() },
                    modifier = Modifier
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF665AFF)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "Google",
                        color = Color.Black,
                    )
                }
            }

        }
    }
}