package com.cookainno.mobile.ui.screens.generation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.LoadingScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsScreen(
    camViewModel: CamViewModel,
    ingredientsViewModel: IngredientsViewModel,
    navController: NavController
) {
    val ingredientList = ingredientsViewModel.ingredients.collectAsState().value
    val generatedRecipes = ingredientsViewModel.recipes.collectAsState().value
    val isLoading by ingredientsViewModel.isLoading.collectAsState()
    val imageCam by camViewModel.fileFromCam.observeAsState()
    val imageGallery by camViewModel.fileFromGallery.observeAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    if (imageCam != null) {
        ingredientsViewModel.detectIngredients(imageCam!!)
        camViewModel.resetImages()
    }
    if (imageGallery != null) {
        ingredientsViewModel.detectIngredients(imageGallery!!)
        camViewModel.resetImages()
    }
    LaunchedEffect(key1 = generatedRecipes) {
        if (generatedRecipes.isNotEmpty()) {
            coroutineScope.launch {
                navController.navigate(NavRoutes.GENERATED.name) {
                    popUpTo(NavRoutes.INGREDIENTS.name) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Scaffold(
        content = { padding ->
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    BottomSheetContent(
                        onCameraClick = {
                            camViewModel.runCamera()
                            showBottomSheet = false
                        },
                        onGalleryClick = {
                            camViewModel.runGallery()
                            showBottomSheet = false
                        }
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    LoadingScreen()
                } else {
                    LazyColumn(verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primary),) {
                        item {
                            Row(
                                Modifier
                                    .background(color = MaterialTheme.colorScheme.primary)
                            ) {
                                IconButton(modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(30.dp)
                                    ),
                                    onClick = {
                                        navController.navigateUp() // check for null ingredients
                                    }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.NavigateBefore,
                                        tint = MaterialTheme.colorScheme.inversePrimary,
                                        contentDescription = "before"
                                    )
                                }
                                Text(
                                    text = "Ingredients",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(horizontal = 52.dp)
                                        .align(Alignment.CenterVertically)
                                )
                                IconButton(modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(30.dp)
                                    ),
                                    onClick = {
                                        ingredientsViewModel.generateRecipes() // check for null ingredients
                                    }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.NavigateNext,
                                        tint = MaterialTheme.colorScheme.inversePrimary,
                                        contentDescription = "Next"
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(
                                    bottomEndPercent = 10,
                                    bottomStartPercent = 10
                                )
                            ),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(items = ingredientList) { index, ingredient ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                TextField(
                                    value = ingredient,
                                    onValueChange = { newValue ->
                                        ingredientsViewModel.updateIngredient(index, newValue)
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.White,
                                        unfocusedIndicatorColor = Color.White,
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(shape = RoundedCornerShape(30.dp))
                                )
                                IconButton(onClick = {
                                    ingredientsViewModel.removeIngredient(index)
                                }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Remove Ingredient"
                                    )
                                }
                            }
                        }
                        item {
                            Row (modifier = Modifier.padding(8.dp)){
                                Button(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .height(49.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.onBackground,
                                    ), onClick = {
                                        ingredientsViewModel.addIngredient("")
                                    }) {
                                    Icon(Icons.Default.Add, contentDescription = "Add Ingredient")
                                    Text("Add Ingredient")
                                }
                                Spacer(modifier = Modifier.width(((screenWidth - 140)/2).dp))
                                IconButton(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp, start = 8.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = RoundedCornerShape(30.dp),
                                        ),
                                    onClick = { showBottomSheet = true },
                                ) {
                                    Icon(
                                        Icons.Default.CameraAlt,
                                        contentDescription = "Open Camera",
                                        tint = MaterialTheme.colorScheme.inversePrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun BottomSheetContent(onCameraClick: () -> Unit, onGalleryClick: () -> Unit) {
    Box(Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp)
        ) {
            TextButton(
                onClick = {
                    onCameraClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                Text(text = "Open Camera")
            }
            TextButton(
                onClick = {
                    onGalleryClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery")
                Text(text = "Open Gallery")
            }
        }
    }
}