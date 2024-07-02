package com.cookainno.mobile.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cookainno.mobile.ui.screens.auth.UserViewModel

@Composable
fun ProfileScreen(userViewModel: UserViewModel) {
    //userViewModel.updateUserData(...) // date format is yyyy-mm-dd
    val userData by userViewModel.userData.collectAsState()
    userViewModel.getUserData()
    Column {
        Button(onClick = {
            userViewModel.signOut()
        }) {
            Text(text = "Sign Out")
        }
        Text(text = "$userData")
    }
}
