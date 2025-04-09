package com.coppel.technicaltest.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coppel.technicaltest.R
import com.coppel.technicaltest.ui.components.LoginBackground

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navigateToHome: () -> Unit
) {
    val state = loginViewModel.loginUiState.value
    LoginBackground()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kotlin),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "The Technical Test",
                        fontSize = 24.sp,
                    )
                    Text(
                        text = "The Technical Test",
                        fontSize = 18.sp,
                    )
                }
            }
            Text(
                text = "Login",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = state.user,
                onValueChange = { loginViewModel.onUserChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("User") },
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { loginViewModel.onPasswordChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Password") },
            )
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Login")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.isBiometricEnabled,
                    onCheckedChange = { loginViewModel.onBiometricChanged(it) }
                )
                Text("Enable fingerprint login")
            }
            Text(
                text = "Or continue with",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                    Text("Google")
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        loginViewModel = LoginViewModel(),
        navigateToHome = {}
    )
}