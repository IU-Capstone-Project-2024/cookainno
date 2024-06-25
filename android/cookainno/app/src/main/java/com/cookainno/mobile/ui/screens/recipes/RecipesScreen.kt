package com.cookainno.mobile.ui.screens.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.ui.NavRoutes
import kotlinx.coroutines.flow.collectLatest

data class RecipeTest(
    val imageResId: Int, val titleResId: Int, val recipe: String
)

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val allRecipes by recipesViewModel.recipes.collectAsState()
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        recipesViewModel.getRecipesSortedByLikes()
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastIndex ->
                if (lastIndex != null && lastIndex >= (allRecipes?.size ?: 0) - 1) {
                    recipesViewModel.getRecipesSortedByLikes()
                }
            }
    }

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
            state = listState,
            columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)
        ) {
            items(allRecipes ?: emptyList()) { recipe ->
                RecipeItem(recipe = recipe, onClick = {
                    recipesViewModel.selectRecipe(recipe)
                    navController.navigate(NavRoutes.DETAILS.name)
                })
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
                painter = rememberAsyncImagePainter(model = recipe.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = recipe.name, modifier = Modifier.padding(8.dp))
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
