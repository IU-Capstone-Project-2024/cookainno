package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
fun RegistrationScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val username by userViewModel.username.collectAsState()
    val email by userViewModel.email.collectAsState()
    val password by userViewModel.password.collectAsState()
    val registrationError by userViewModel.registrationError.collectAsState()
    val navigateToConfirmation by userViewModel.navigateToConfirmation.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current //recheck

    val usernameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    LaunchedEffect(navigateToConfirmation) {
        if (navigateToConfirmation) {
            userViewModel.resetToConfirmation()
            navController.navigate(NavRoutes.CONFIRMATION.name)
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
                    text = "Sign Up",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                if (registrationError != null) {
                    Text(
                        text = registrationError ?: "Registration Error",
                        color = Color.Red,
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
                        onNext = { emailFocusRequester.requestFocus() }
                    ),
                    onValueChange = userViewModel::onUsernameChanged
                )
                TextField(
                    label = { Text(text = "Email") },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester),
                    value = email,
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
                    onValueChange = userViewModel::onEmailChanged
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
                        userViewModel.signUp()
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text(text = "Sign Up")
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Already have an Account?",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Sign In",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier.clickable {
                            navController.navigate(NavRoutes.LOGIN.name)
                        })
                }
            }
        }
    }
}
