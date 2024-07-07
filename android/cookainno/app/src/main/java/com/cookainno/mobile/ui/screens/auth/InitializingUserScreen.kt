package com.cookainno.mobile.ui.screens.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cookainno.mobile.R
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.generation.animatedGradientBackground
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InitializingUserScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val height by userViewModel.height.collectAsState()
    val weight by userViewModel.weight.collectAsState()
    val date by userViewModel.date.collectAsState()
    val navigateToMain by userViewModel.navigateToMain.collectAsState()

    val heightFocusRequester = remember { FocusRequester() }
    val weightFocusRequester = remember { FocusRequester() }
    val dateFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    userViewModel.initUserId()
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
                ), contentAlignment = Alignment.Center
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

                TextField(
                    label = { Text("Height") },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .fillMaxWidth()
                        .focusRequester(heightFocusRequester),
                    value = height,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { weightFocusRequester.requestFocus() }
                    ),
                    onValueChange = {
                        userViewModel.onHeightChange(it)
                    })
                TextField(
                    label = { Text("Weight") },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester),
                    value = weight,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { dateFocusRequester.requestFocus() }
                    ),
                    onValueChange = {
                        userViewModel.onWeightChange(it)
                    })
                DateInputField(
                    userViewModel = userViewModel,
                    dateFocusRequester = dateFocusRequester
                )
                Button(
                    onClick = {
                        userViewModel.updateUserData(
                            height.toInt(),
                            weight.toInt(),
                            date
                        )
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Composable
fun DateInputField(userViewModel: UserViewModel, dateFocusRequester: FocusRequester) {
    var date by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    TextField(
        label = { Text("Date (yyyy-mm-dd)") },
        modifier = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .focusRequester(dateFocusRequester),
        value = date,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                userViewModel.signUp()
            }
        ),
        onValueChange = { newValue ->
            date = newValue
            userViewModel.onDateChange(date)
        },
        trailingIcon = {
            IconButton(onClick = {
                showDatePickerDialog(context) { selectedDate ->
                    date = selectedDate
                    userViewModel.onDateChange(date)
                }
            }) {
                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Select date")
            }
        }
    )
}

fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        val formattedDate = "$selectedYear-" +
                "${
                    (selectedMonth + 1).toString().padStart(2, '0')
                }-${selectedDay.toString().padStart(2, '0')}"
        onDateSelected(formattedDate)
    }, year, month, day).show()
}