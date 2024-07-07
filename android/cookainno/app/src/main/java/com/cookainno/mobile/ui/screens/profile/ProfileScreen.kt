package com.cookainno.mobile.ui.screens.profile

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.R
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.recipes.TopBar
import kotlin.random.Random

@Composable
fun ProfileScreen(userViewModel: UserViewModel) {
    //userViewModel.updateUserData(...) // date format is yyyy-mm-dd
    val userData by userViewModel.userData.collectAsState()
    val weight by userViewModel.weight.collectAsState()
    val height by userViewModel.height.collectAsState()
    val date by userViewModel.date.collectAsState()

    val imageList = listOf(
        R.drawable.potato, R.drawable.strawberry, R.drawable.watermelon, R.drawable.peach
    )

    val randomImage = imageList[Random.nextInt(imageList.size)]

    val adviceStrings by userViewModel.advice.collectAsState()
    val randomAdvice = remember { adviceStrings }

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
                        UserDataSection(weight, height, date, userViewModel::onWeightChange)
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
fun UserDataRow(label: String, value: String, onChange: (String) -> Unit) {
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
            onClick = { onChange("500") }, // change with textfield!
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
fun UserDataSection(weight: String, height: String, date: String, onWeightChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        UserDataRow(
            label = "Weight",
            value = weight,
            onChange = onWeightChange)
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        UserDataRow(
            label = "Height",
            value = height,
            onChange = {  })
    }
}