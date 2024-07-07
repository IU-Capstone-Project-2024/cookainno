package com.cookainno.mobile.ui.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.NavRoutes

@Composable
fun InitializingUserScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val height by userViewModel.height.collectAsState()
    val weight by userViewModel.weight.collectAsState()
    val date by userViewModel.date.collectAsState()
    val navigateToMain by userViewModel.navigateToMain.collectAsState()
    userViewModel.initUserId()
    if (navigateToMain) {
        LaunchedEffect(Unit) {
            navController.navigate(NavRoutes.HOME.name)
        }
    }
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.Center) {
            Column {
                OutlinedTextField(
                    label = { Text("Height") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = height,
                    onValueChange = {
                        userViewModel.onHeightChange(it)
                    })
                OutlinedTextField(
                    label = { Text("Weight") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = weight,
                    onValueChange = {
                        userViewModel.onWeightChange(it)
                    })
                OutlinedTextField(
                    label = { Text("Date FORMAT yyyy-mm-dd") },
                    value = date,
                    onValueChange = {
                        userViewModel.onDateChange(it)
                    })
                Button(onClick = {
                    userViewModel.updateUserData(height.toInt(), weight.toInt(), date)
                }) {
                    Text(text = "Submit")
                }
            }
        }
    }
}
