package com.cookainno.mobile.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel

@Composable
fun RecipeDetailsScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val recipe by recipesViewModel.selectedRecipe.collectAsState()
    recipe?.let {
        RecipeDetails(recipe = it, navController)
    } ?: run {
        Text(text = "Recipe not found")
    }
}

@Composable
fun RecipeDetails(recipe: Recipe, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            TopBarDetails(
                shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
                recipe = recipe, navController = navController
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceBright,
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = recipe.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
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
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 14.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "favorite",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = recipe.likes.toString(),
                            Modifier.padding(end = 14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = stringResource(id = R.string.ingredients),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 8.dp)
                )
                val ingredientsList = recipe.ingredients.split(",").map { it.trim() }
                Column {
                    ingredientsList.forEach { ingredient ->
                        Text(
                            text = "• $ingredient",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = stringResource(id = R.string.instructions),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 8.dp)
                )
                val instructionList = recipe.instructions.split(".").map { it.trim() }
                    .filter { it.isNotEmpty() }
                Column {
                    instructionList.forEach { instruction ->
                        Text(
                            text = "• $instruction",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarDetails(
    recipe: Recipe,
    shape: RoundedCornerShape,
    navController: NavHostController,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary, shape = shape, modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9f)
            .height(125.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 14.dp, bottom = 9.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Surface(
                    modifier = Modifier,
                    shape = RoundedCornerShape(30.dp),
                ) {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            .size(52.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = recipe.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceBright,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clip(RoundedCornerShape(30.dp))
                        .height(55.dp)
                        .width(350.dp)
                        .padding(14.dp),
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        }
    }
}
