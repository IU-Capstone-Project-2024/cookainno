package com.cookainno.mobile.ui.screens.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cookainno.mobile.R
import com.cookainno.mobile.ui.screens.camera.CamViewModel

@Composable
fun RecipesScreen(camViewModel: CamViewModel, recipesViewModel: RecipesViewModel) {
    val res by recipesViewModel.okExample.collectAsState()
    recipesViewModel.example()
    //Text(text = "Test auth call: ${if (res) "Res OK" else "Res FAIL"}")
    RecipesScreen()
}

data class Recipe(
    val imageResId: Int,
    val titleResId: Int
)

@Composable
fun RecipesScreen() {
    val recipes = listOf(
        Recipe(R.drawable.apple, R.string.apple),
        Recipe(R.drawable.kartoshka, R.string.kartoshka),
        Recipe(R.drawable.burger, R.string.burger),
        Recipe(R.drawable.carri, R.string.carri),
        Recipe(R.drawable.meat, R.string.meat),
        Recipe(R.drawable.kartoshka, R.string.kartoshka),
        Recipe(R.drawable.burger, R.string.burger),
        Recipe(R.drawable.carri, R.string.carri),
        Recipe(R.drawable.meat, R.string.meat),
        Recipe(R.drawable.kartoshka, R.string.kartoshka),
        Recipe(R.drawable.burger, R.string.burger),
        Recipe(R.drawable.carri, R.string.carri),
        Recipe(R.drawable.meat, R.string.meat),
    )


    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(searchQuery.value) {
            searchQuery.value = it
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Number of columns in the grid
            contentPadding = PaddingValues(16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeItem(recipe = recipe, onClick = {
                })
            }
        }
    }

}

@Composable
fun TopBar(query: TextFieldValue, onQueryChanged: (TextFieldValue) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { onQueryChanged(it) },
            label = { Text("Pupa") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        // Handle camera icon click
                    },
                    modifier = Modifier.size(60.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {
                // Handle camera icon click
            },
            modifier = Modifier.size(60.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.camera), // Replace with your camera icon resource
                contentDescription = "Camera",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .background(Color.LightGray, RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = recipe.imageResId),
            contentDescription = null,
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                //.padding(8.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = recipe.titleResId))
        Spacer(modifier = Modifier.height(16.dp))
    }
}
