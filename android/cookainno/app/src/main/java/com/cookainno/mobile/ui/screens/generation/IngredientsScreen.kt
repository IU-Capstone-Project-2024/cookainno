package com.cookainno.mobile.ui.screens.generation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cookainno.mobile.ui.NavRoutes
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
    val showCamera by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

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
        topBar = {
            TopAppBar(
                title = { Text("Add Ingredients") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        ingredientsViewModel.generateRecipes() // check for null ingredients
                    }) {
                        Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = "Next")
                    }
                }
            )
        },
        floatingActionButton = {
            if (showCamera) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    FloatingActionButton(
                        onClick = { showBottomSheet = true }
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Open Camera")
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { padding ->
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    BottomSheetContent(
                        onCameraClick = camViewModel::runCamera,
                        onGalleryClick = camViewModel::runGallery
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(items = ingredientList) { index, ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = ingredient,
                            onValueChange = { newValue ->
                                ingredientsViewModel.updateIngredient(index, newValue)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            ingredientsViewModel.removeIngredient(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove Ingredient")
                        }
                    }
                }
                item {
                    Button(onClick = {
                        ingredientsViewModel.addIngredient("")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Ingredient")
                        Text("Add Ingredient")
                    }
                }
            }
        }
    )
}

@Composable
fun BottomSheetContent(onCameraClick: () -> Unit, onGalleryClick: () -> Unit) {
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
