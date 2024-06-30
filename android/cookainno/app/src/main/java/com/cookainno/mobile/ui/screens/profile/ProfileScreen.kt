package com.cookainno.mobile.ui.screens.profile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.cookainno.mobile.ui.screens.auth.UserViewModel

@Composable
fun ProfileScreen(userViewModel: UserViewModel) {
    //userViewModel.updateUserData(...) // date format is yyyy-mm-dd
    Button(onClick = {
        userViewModel.signOut()
    }) {
        Text(text = "Sign Out")
    }
}
