package com.cookainno.mobile.ui.screens.generation

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.GeneratedRecipe
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.ui.NavRoutes

@Composable
fun GeneratedRecipeScreen(
    ingredientsViewModel: IngredientsViewModel,
    navController: NavHostController
) {
    val recipes by ingredientsViewModel.recipes.collectAsState()
    if (recipes.isNotEmpty()) {
        RecipeDetailsGen(recipe = recipes.first(), navController)
    }
}


@Composable
fun RecipeDetailsGen(recipe: GeneratedRecipe, navController: NavHostController) {
    Log.d("ROSENBERG", "RecipeDetailsGen: ${recipe}")

    var isLiked = false;
    var liked by rememberSaveable {
        mutableStateOf(isLiked)
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    RecipeImage(recipe = recipe)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Spacer(modifier = Modifier.height((screenHeight / 2.5 - 30).dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight()
                        .background(
                            color = Color.White,
                            RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
                        )
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                text = recipe.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 22.dp)
                            )
                            IconButton(onClick = {
                                /*if (liked) {
                                    removeLike(recipe)
                                    recipe.likes--
                                    recompose.value++
                                    recompose.value--
                                    liked = false
                                } else {
                                    like(recipe)
                                    recipe.likes++
                                    recompose.value++
                                    recompose.value--
                                    liked = true
                                }*/
                            }) {
                                Icon(
                                    imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "favorite",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.ingredients),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        Column {
                            recipe.ingredients.forEach { ingredient ->
                                Text(
                                    text = "• $ingredient",
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = stringResource(id = R.string.instructions),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        val instructionList = recipe.instructions.split(".").map { it.trim() }
                            .filter { it.isNotEmpty() }
                        Column {
                            instructionList.forEach { instruction ->
                                Text(
                                    text = "• $instruction",
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeImage(recipe: GeneratedRecipe) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val imageHeight = remember { screenHeight / 2.5 }

    Image(
        painter = rememberAsyncImagePainter(model = recipe.imageUrl),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight.dp),
        contentScale = ContentScale.Crop
    )
}