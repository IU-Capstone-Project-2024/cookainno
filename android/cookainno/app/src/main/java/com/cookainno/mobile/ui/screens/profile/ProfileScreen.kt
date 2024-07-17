package com.cookainno.mobile.ui.screens.profile

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.UserDataResponse
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.recipes.TopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(userViewModel: UserViewModel, pic: Int) {
    //userViewModel.updateUserData(...) // date format is yyyy-mm-dd
    val userData by userViewModel.userData.collectAsState()

    val imageList = listOf(
        R.drawable.potato, R.drawable.strawberry, R.drawable.watermelon, R.drawable.peach
    )

    val randomImage = imageList[pic]

    userViewModel.getUserData()
    val randomAdvice = remember { userViewModel.advice.value }

    Column {
        TopBar(
            isMain = false,
            isName = true,
            shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
            query = TextFieldValue(),
            navController = rememberNavController(),
            onQueryChanged = {},
        ) {}

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Image(
                                painter = painterResource(id = randomImage),
                                contentDescription = "Random Image",
                                modifier = Modifier
                                    .size(80.dp)
                                    .align(Alignment.Center)
                                    .padding(5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            userData?.let {
                                Text(
                                    text = it.username,
                                    color = MaterialTheme.colorScheme.scrim,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            userData?.let {
                                Text(
                                    text = it.email, color = MaterialTheme.colorScheme.scrim
                                )
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)) // Add shadow here
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Daily Advice",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = randomAdvice,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)) // Add shadow here
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Parameters",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        UserDataSection(
                            userViewModel,
                            userData = userData,
                            LocalContext.current
                        )
                    }
                }

                Button(onClick = {
                    userViewModel.signOut()
                }) {
                    Text(text = "Sign Out")
                }
            }
        }
    }
}


@Composable
fun UserDataRow(
    userViewModel: UserViewModel,
    label: String,
    value: String,
    onRedactClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        if (label != "Daily Calories") TextButton(
            onClick = onRedactClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.scrim,
                    shape = RoundedCornerShape(20.dp)
                )
                .height(40.dp)
        ) {
            Text(
                text = "✎",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserDataSection(
    userViewModel: UserViewModel,
    userData: UserDataResponse?,
    context: Context
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        UserDataRow(
            userViewModel = userViewModel,
            label = "Weight",
            value = userData?.weight.toString(),

            onRedactClick = {
                showInputDialog(
                    context = context,
                    "Change Weight",
                    onInputReceived = { input ->
                        userViewModel.updateUserData(
                            weight = input.toInt(),
                            height = userData?.height!!,
                            date = userData.dateOfBirth
                        )
                    })
            })
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        UserDataRow(
            userViewModel = userViewModel,
            label = "Height",
            value = userData?.height.toString(),
            onRedactClick = {
                showInputDialog(
                    context = context,
                    "Change Height",
                    onInputReceived = { input ->
                        userViewModel.updateUserData(
                            weight = userData?.weight!!,
                            height = input.toInt(),
                            date = userData.dateOfBirth
                        )
                    })
            })
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        if (userData != null) {
            UserDataRow(
                userViewModel = userViewModel,
                label = "Daily Calories",
                value = userViewModel.calculateCalories(
                    userData.weight,
                    userData.height,
                    userViewModel.calculateAge(userData.dateOfBirth)
                ).toString(),
                onRedactClick = {
                }
            )
        }
    }
}

fun showInputDialog(context: Context, title: String, onInputReceived: (String) -> Unit) {
    val dialogBuilder = AlertDialog.Builder(context)
    val inputField = EditText(context).apply {
        inputType = InputType.TYPE_CLASS_NUMBER
        hint = "Enter number"
        setPadding(45, 24, 45, 24)

        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                setBackgroundColor(Color.White.toArgb())
            } else {
                setBackgroundColor(Color.White.toArgb())
            }
        }
    }

    dialogBuilder.apply {
        setTitle(title)
        setView(inputField)
        setPositiveButton("OK") { _, _ ->
            onInputReceived(inputField.text.toString())
        }
        setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
    }

    val dialog = dialogBuilder.create()

    inputField.requestFocus()
    dialog.window?.setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    inputField.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onInputReceived(inputField.text.toString())
            dialog.dismiss()
            true
        } else {
            false
        }
    }

    dialog.setCanceledOnTouchOutside(false)
    dialog.show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.let { button ->
        button.setTextColor(Color.Gray.toArgb())
        button.gravity = Gravity.CENTER
        button.setPadding(24, 16, 24, 16)
    }

    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.let { button ->
        button.setTextColor(Color.Gray.toArgb())
        button.gravity = Gravity.CENTER
        button.setPadding(24, 16, 24, 16)
    }
}