package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.NavRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(authViewModel: AuthViewModel, navController: NavHostController) {
    val username by authViewModel.username.collectAsState()
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()
    val registrationError by authViewModel.registrationError.collectAsState()
    val navigateToConfirmation by authViewModel.navigateToConfirmation.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current //recheck

    LaunchedEffect(navigateToConfirmation) {
        if (navigateToConfirmation) {
            navController.navigate(NavRoutes.CONFIRMATION.name)
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(300.dp)
                    .padding(bottom = 30.dp)
            ) {
                Text(text = "Sign Up", fontSize = 25.sp, modifier = Modifier.padding(3.dp))
                if (registrationError != null) {
                    Text(
                        text = registrationError?:"Registration Error",
                        color = Color.Red,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                OutlinedTextField(
                    label = { Text(text = "Username") },
                    modifier = Modifier.padding(3.dp),
                    value = username,
                    onValueChange = authViewModel::onUsernameChanged
                )
                OutlinedTextField(
                    label = { Text(text = "Email") },
                    modifier = Modifier.padding(3.dp),
                    value = email,
                    onValueChange = authViewModel::onEmailChanged
                )
                OutlinedTextField(
                    label = { Text(text = "Password") },
                    modifier = Modifier.padding(3.dp),
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = authViewModel::onPasswordChanged
                )
                Button(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(3.dp),
                    onClick = {
                        authViewModel.signUp()
                        keyboardController?.hide()
                        navController.navigate(NavRoutes.CONFIRMATION.name)
                    }
                ) {
                    Text(text = "Sign Up")
                }
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "Already have an Account?")
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Sign In",
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController.navigate(NavRoutes.LOGIN.name)
                        })
                }
            }
        }
    }
}
