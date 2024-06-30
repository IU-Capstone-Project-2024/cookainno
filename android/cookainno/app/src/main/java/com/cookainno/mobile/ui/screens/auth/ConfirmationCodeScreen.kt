package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.NavRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfirmationCodeScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val confirmationCode by userViewModel.confirmationCode.collectAsState()
    val navigateToInit by userViewModel.navigateToInit.collectAsState()
    LaunchedEffect(Unit) {
        if (navigateToInit) {
            userViewModel.resetToInit()
            navController.navigate(NavRoutes.USERINIT.name)
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
                Text(
                    text = "Confirmation code from email",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(3.dp)
                )
                OutlinedTextField(
                    label = { Text(text = "Code") },
                    modifier = Modifier.padding(3.dp),
                    value = confirmationCode,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = userViewModel::onConfirmationChange
                )
                Button(
                    onClick = {
                        userViewModel.confirmCode()
                    },
                    modifier = Modifier.padding(3.dp)
                ) {
                    Text(text = "Send Code")
                }
            }
        }
    }
}
