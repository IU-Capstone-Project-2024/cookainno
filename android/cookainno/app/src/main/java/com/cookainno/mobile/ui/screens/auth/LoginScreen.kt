package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
fun LoginScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val registrationError by userViewModel.registrationError.collectAsState()
    val username by userViewModel.username.collectAsState()
    val password by userViewModel.password.collectAsState()
    val navigateToMain by userViewModel.navigateToMain.collectAsState()
    if (navigateToMain) {
        LaunchedEffect(Unit) {
            navController.navigate(NavRoutes.HOME.name)
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
                Text(text = "Sign In", fontSize = 25.sp, modifier = Modifier.padding(3.dp))
                registrationError?.let {
                    Text(
                        text = it, color = Color.Red,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                OutlinedTextField(
                    label = { Text(text = "Username") },
                    modifier = Modifier.padding(3.dp),
                    value = username,
                    onValueChange = userViewModel::onUsernameChanged
                )
                OutlinedTextField(
                    label = { Text(text = "Password") },
                    modifier = Modifier.padding(3.dp),
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = userViewModel::onPasswordChanged
                )
                Button(
                    onClick = {
                        userViewModel.signIn()
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(3.dp)
                ) {
                    Text(text = "Sign In")
                }
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "Want to create an account?")
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Sign Up",
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController.navigate(NavRoutes.REGISTRATION.name)
                        })
                }
            }
        }
    }
}
