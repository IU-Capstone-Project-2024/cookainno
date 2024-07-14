package com.cookainno.mobile.ui.screens.recipes

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.Recipe

@Composable
fun RecipeDetailsScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val recipe by recipesViewModel.selectedRecipe.collectAsState()
    recipe?.let {
        RecipeDetails(
            recipe = it,
            navController,
            recipesViewModel.isFavourite(it),
            recipesViewModel::addFavouriteRecipe,
            recipesViewModel::deleteFavouriteRecipe
        )
    } ?: run {
        Text(text = "Recipe not found")
    }
}

@Composable
fun RecipeDetails(
    recipe: Recipe,
    navController: NavHostController,
    isLiked: Boolean,
    like: (recipe: Recipe) -> Unit,
    removeLike: (recipe: Recipe) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    var liked by rememberSaveable {
        mutableStateOf(isLiked)
    }
    val recompose = remember {
        mutableStateOf(0)
    }

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
            /*item {
                TopBarDetails(
                    //shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
                    recipe = recipe, navController = navController
                )

                //Spacer(modifier = Modifier.height(10.dp))
            }*/
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight()
                        .background(
                            color = Color.White,
                            RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
                        )
                    //.clip(shape = RoundedCornerShape(topStartPercent = 30))
                    //.clip(RoundedCornerShape(20.dp)),
                ) {
                    Column(
                        /*.background(
                            MaterialTheme.colorScheme.surfaceBright,
                            RoundedCornerShape(20.dp)
                        )*/
                    ) {

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
                                if (liked) {
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
                                }
                            }) {
                                Icon(
                                    imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "favorite",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${recipe.likes + recompose.value}",
                                Modifier.padding(end = 14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    )
                    {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = stringResource(id = R.string.ingredients),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                //.padding(horizontal = 30.dp)
                                .padding(bottom = 8.dp)
                        )


                        val ingredientsList = recipe.ingredients.split(",").map { it.trim() }
                        Column {
                            ingredientsList.forEach { ingredient ->
                                Text(
                                    text = "• $ingredient",
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 2.dp
                                    )
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
                                //.padding(horizontal = 30.dp)
                                .padding(bottom = 8.dp)
                        )

                        val instructionList = recipe.instructions.split(".").map { it.trim() }
                            .filter { it.isNotEmpty() }
                        Column {
                            instructionList.forEach { instruction ->
                                Text(
                                    text = "• $instruction",
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Surface(
            modifier = Modifier.padding(12.dp),
            shape = RoundedCornerShape(30.dp),
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        color = Color.White
                    )
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun RecipeImage(recipe: Recipe) {
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