package com.cookainno.mobile.ui.screens.generation

import android.util.Log
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
import com.cookainno.mobile.data.model.GeneratedRecipe
import com.cookainno.mobile.ui.NavRoutes

@Composable
fun GeneratedRecipeScreen(ingredientsViewModel: IngredientsViewModel, navController: NavHostController) {
    val recipes by ingredientsViewModel.recipes.collectAsState()
    if (recipes.isNotEmpty()) {
        RecipeDetailsGen(recipe = recipes.first(), navController)
    }
}


@Composable
fun RecipeDetailsGen(recipe: GeneratedRecipe, navController: NavHostController) {
    Log.d("ROSENBERG", "RecipeDetailsGen: ${recipe}")
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
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = stringResource(id = R.string.ingredients),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 8.dp)
                )
                Column {
                    recipe.ingredients.forEach { ingredient ->
                        Text(
                            text = "• $ingredient",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 2.dp)
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
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarDetails(
    recipe: GeneratedRecipe,
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
                            navController.navigate(NavRoutes.RECIPES.name) {
                                navController.popBackStack()
                            }
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
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clip(RoundedCornerShape(30.dp))
                        .height(55.dp)
                        .width(350.dp)
                        .padding(14.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
