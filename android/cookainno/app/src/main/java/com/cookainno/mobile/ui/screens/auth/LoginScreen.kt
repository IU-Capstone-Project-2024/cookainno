package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cookainno.mobile.R
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.generation.animatedGradientBackground

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val registrationError by userViewModel.registrationError.collectAsState()
    val username by userViewModel.username.collectAsState()
    val password by userViewModel.password.collectAsState()
    val navigateToMain by userViewModel.navigateToMain.collectAsState()

    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    if (navigateToMain) {
        LaunchedEffect(Unit) {
            navController.navigate(NavRoutes.HOME.name)
        }
    }
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .animatedGradientBackground(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceBright
                    ),
                )
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(300.dp)
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Sign In",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                registrationError?.let {
                    Text(
                        text = it, color = Color.Red,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                TextField(
                    label = { Text(text = "Username") },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .fillMaxWidth()
                        .focusRequester(usernameFocusRequester),
                    value = username,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() }
                    ),
                    onValueChange = userViewModel::onUsernameChanged
                )
                TextField(
                    label = { Text(text = "Password") },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    value = password,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            userViewModel.signUp()
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = userViewModel::onPasswordChanged
                )
                Button(
                    onClick = {
                        userViewModel.signIn()
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text(text = "Sign In")
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Want to create an account?",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Sign Up",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier.clickable {
                            navController.navigate(NavRoutes.REGISTRATION.name)
                        })
                }
            }
        }
    }
}
