package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.generation.animatedGradientBackground

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfirmationCodeScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val confirmationCode by userViewModel.confirmationCode.collectAsState()
    val navigateToInit by userViewModel.navigateToInit.collectAsState()

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        if (navigateToInit) {
            userViewModel.resetToInit()
            navController.navigate(NavRoutes.USERINIT.name)
        }
        focusRequester.requestFocus()
    }
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()
            .animatedGradientBackground(
                colors = listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.surfaceBright
                ),
            )) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(300.dp)
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    text = "Confirmation code from email",
                    fontSize = 25.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 5.dp)
                )
                TextField(
                    label = { Text(text = "Enter Code") },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = confirmationCode,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            userViewModel.confirmCode()
                        }
                    ),
                    onValueChange = userViewModel::onConfirmationChange
                )
                Button(
                    onClick = {
                        userViewModel.confirmCode()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text(text = "Send Code")
                }
            }
        }
    }
}
