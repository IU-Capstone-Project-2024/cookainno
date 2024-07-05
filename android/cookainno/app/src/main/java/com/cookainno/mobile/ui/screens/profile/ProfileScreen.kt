package com.cookainno.mobile.ui.screens.profile

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
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
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.UserDataResponse
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.recipes.TopBar
import kotlin.random.Random

@Composable
fun ProfileScreen(userViewModel: UserViewModel) {
    //userViewModel.updateUserData(...) // date format is yyyy-mm-dd
    val userData by userViewModel.userData.collectAsState()

    val imageList = listOf(
        R.drawable.potato, R.drawable.strawberry, R.drawable.watermelon, R.drawable.peach
    )

    val randomImage = imageList[Random.nextInt(imageList.size)]

    val adviceStrings = listOf(
        stringResource(id = R.string.str1),
        stringResource(id = R.string.str2),
        stringResource(id = R.string.str3),
        stringResource(id = R.string.str4),
        stringResource(id = R.string.str5),
        stringResource(id = R.string.str6),
        stringResource(id = R.string.str7),
        stringResource(id = R.string.str8),
        stringResource(id = R.string.str9),
        stringResource(id = R.string.str10),
        stringResource(id = R.string.str11),
        stringResource(id = R.string.str12)
    )
    val randomAdvice = remember { adviceStrings.random() }

    userViewModel.getUserData()
    Column {
        TopBar(
            isMain = false,
            isName = true,
            shape = RoundedCornerShape(bottomStartPercent = 35, bottomEndPercent = 35),
            query = TextFieldValue(),
            navController = rememberNavController(),
            onQueryChanged = {},
        ) {}

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                if (userData != null) {
                    // Display user profile using userDataState
                    Text("Username: ${userData!!.username.toString()}")
                    Text("Email: ${userData!!.email}")
                    Text("Height: ${userData!!.height}")
                    Text("Weight: ${userData!!.weight}")
                    Text("Date: ${userData!!.date}")
                }
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
                        .clip(RoundedCornerShape(16.dp)), colors = CardColors(
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
                        .clip(RoundedCornerShape(16.dp)), colors = CardColors(
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
                            text = "Parameters", style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                            ), color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        UserDataSection(userViewModel, userData = userData, LocalContext.current)
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
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            onClick = onRedactClick,

            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.scrim,
                    shape = RoundedCornerShape(20.dp)
                )
                .width(100.dp)
                .height(40.dp)
        ) {
            Text(
                text = "$value  ✎",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

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
                        //userViewModel.onWeightChange(input)
                        userViewModel.updateUserData(
                            weight = input.toInt(),
                            height = userData?.height!!,
                            date = userData.date
                        )
                    })
            })
        Divider(color = Color.Gray, thickness = 1.dp)
        UserDataRow(
            userViewModel = userViewModel,
            label = "Height",
            value = userData?.height.toString(),
            onRedactClick = {
                showInputDialog(
                    context = context,
                    "Change Weight",
                    onInputReceived = { input ->
                        userViewModel.onWeightChange(input)
                    })
                //userViewModel.updateUserData(height.toInt(), weight.toInt(), date)

                //userViewModel.updateUserData(date,= weight = ,height=)
            })
        Divider(color = Color.Gray, thickness = 1.dp)
        UserDataRow(
            userViewModel = userViewModel,
            label = "Daily Calories",
            value = "3000000",
            onRedactClick = {
                showInputDialog(
                    context = context,
                    "Change Weight",
                    onInputReceived = { input ->
                        userViewModel.onWeightChange(input)
                    })
                //userViewModel.updateUserData(date,= weight = ,height=)
            })
        Divider(color = Color.Gray, thickness = 1.dp)
        /*UserDataRow(
            userViewModel = userViewModel,
            label = "Date",
            value = userData!!.date,
            onRedactClick = {
                showInputDialog(
                    context = context,
                    "Change Weight",
                    onInputReceived = { input ->
                        userViewModel.onWeightChange(input)
                    })
                //userViewModel.updateUserData(date,= weight = ,height=)
            })*/
    }
}

fun showInputDialog(context: Context, title: String, onInputReceived: (String) -> Unit) {
    val dialogBuilder = AlertDialog.Builder(context)
    val inputField = EditText(context)
    inputField.inputType = InputType.TYPE_CLASS_NUMBER
    dialogBuilder.setTitle(title)
    dialogBuilder.setView(inputField)
    dialogBuilder.setPositiveButton("OK") { _, _ ->
        onInputReceived(inputField.text.toString())
    }
    dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.cancel()
    }
    dialogBuilder.show()
}