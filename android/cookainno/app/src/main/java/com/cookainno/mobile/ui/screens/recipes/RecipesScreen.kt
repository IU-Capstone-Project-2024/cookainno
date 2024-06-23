package com.cookainno.mobile.ui.screens.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cookainno.mobile.R
import com.cookainno.mobile.ui.NavRoutes
import kotlin.math.round

data class Recipe(
    val imageResId: Int, val titleResId: Int, val recipe: String
)

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val recipes = listOf(
        Recipe(R.drawable.apple, R.string.apple, "apple"),
        Recipe(
            R.drawable.kartoshka, R.string.kartoshka, "muka, water, kartofel, brown kraska, sugar"
        ),
        Recipe(R.drawable.burger, R.string.burger, "bulka burgernaya, meat, salad, tomato, onion"),
        Recipe(R.drawable.carri, R.string.carri, "yaneznayu"),
        Recipe(R.drawable.meat, R.string.meat, "meat"),
        Recipe(R.drawable.apple, R.string.apple, "apple"),
        Recipe(
            R.drawable.kartoshka, R.string.kartoshka, "muka, water, kartofel, brown kraska, sugar"
        ),
        Recipe(R.drawable.burger, R.string.burger, "bulka burgernaya, meat, salad, tomato, onion"),
        Recipe(R.drawable.carri, R.string.carri, "yaneznayu"),
        Recipe(R.drawable.meat, R.string.meat, "meat"),
        Recipe(R.drawable.apple, R.string.apple, "apple"),
        Recipe(
            R.drawable.kartoshka, R.string.kartoshka, "muka, water, kartofel, brown kraska, sugar"
        ),
        Recipe(R.drawable.burger, R.string.burger, "bulka burgernaya, meat, salad, tomato, onion"),
        Recipe(R.drawable.carri, R.string.carri, "yaneznayu"),
        Recipe(R.drawable.meat, R.string.meat, "meat"),
    )
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
            searchQuery.value,
            navController
        ) {
            searchQuery.value = it
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeItem(recipe = recipe, onClick = {})
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    shape: RoundedCornerShape,
    query: TextFieldValue,
    navController: NavHostController,
    onQueryChanged: (TextFieldValue) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary, shape = shape, modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { onQueryChanged(it) },
                    label = {
                        Text(
                            "Search",
                            color = MaterialTheme.colorScheme.inversePrimary,
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {

                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",
                                tint = MaterialTheme.colorScheme.inversePrimary
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30.dp))
                        .height(52.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )
                Spacer(modifier = Modifier.width(20.dp))
                Surface(
                    modifier = Modifier,
                    shape = RoundedCornerShape(30.dp),
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(NavRoutes.INGREDIENTS.name)
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            .size(52.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "camera",
                            tint = MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {

    var isLiked by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .padding(6.dp)
            .background(MaterialTheme.colorScheme.surfaceBright, RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = recipe.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = recipe.titleResId), modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }
        IconButton(
            onClick = {
                isLiked = !isLiked
            }, modifier = Modifier
                .size(60.dp)
                .align(alignment = Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = (if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder),
                contentDescription = "favorite",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}

