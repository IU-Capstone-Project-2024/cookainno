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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cookainno.mobile.R
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.ui.NavRoutes
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val allRecipes by recipesViewModel.recipes.collectAsState()
    val isRefreshing by recipesViewModel.isRefreshing.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchingState by rememberSaveable {
        mutableStateOf(false)
    }

    val state = rememberPullToRefreshState()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        recipesViewModel.getRecipesSortedByLikes()
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastIndex ->
                if (lastIndex != null && lastIndex >= (allRecipes?.size ?: 0) - 1) {
                    if (!isSearchingState) {
                        recipesViewModel.getRecipesSortedByLikes()
                    } else {
                        recipesViewModel.searchRecipes(searchQuery.text)
                    }
                }
            }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            isMain = true,
            isName = false,
            shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
            query = searchQuery,
            navController = navController,
            onQueryChanged = { searchQuery = it },
            onSearchClick = {
                isSearchingState = true
                recipesViewModel.resetPagination()
                recipesViewModel.searchRecipes(searchQuery.text)
            }
        )
        PullToRefreshBox(
            state = state,
            isRefreshing = isRefreshing,
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth(),
            onRefresh = {
                recipesViewModel.resetPagination()
                recipesViewModel.getRecipesSortedByLikes()
            }) {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(allRecipes ?: emptyList()) { recipe ->
                    RecipeItem(recipe = recipe, recipesViewModel = recipesViewModel, onCardClick = {
                        recipesViewModel.selectRecipe(recipe)
                        navController.navigate(NavRoutes.DETAILS.name)
                    })
                }
            }
        }
    }
}

@Composable
fun TopBar(
    isMain: Boolean, // define main or favourites screen,
    isName: Boolean, //define profile and pages with only app name topbar
    shape: RoundedCornerShape,
    query: TextFieldValue,
    navController: NavHostController,
    onQueryChanged: (TextFieldValue) -> Unit,
    onSearchClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        color = MaterialTheme.colorScheme.primary, shape = shape, modifier = Modifier
            .fillMaxWidth()
            .height(if (!isName) 125.dp else 75.dp)
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
            if (!isName) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        singleLine = true,
                        value = query,
                        onValueChange = { onQueryChanged(it) },

                        label = {
                            Text(
                                "Search",
                                color = MaterialTheme.colorScheme.inversePrimary,
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                onSearchClick()
                            }
                        ),
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                onClick = {
                                    onSearchClick()
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
                            .clip(RoundedCornerShape(30.dp)),
                        shape = RoundedCornerShape(30.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                    if (isMain) {
                        IconButton(
                            onClick = {
                                navController.navigate(NavRoutes.INGREDIENTS.name)
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                                /* .animatedGradientBackground(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.surfaceBright
                                ),
                            )*/
                                .align(Alignment.CenterVertically)
                                .size(57.dp)
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
}

@Composable
fun RecipeItem(recipe: Recipe, recipesViewModel: RecipesViewModel, onCardClick: () -> Unit) {
    var liked by rememberSaveable {
        mutableStateOf(recipesViewModel.isFavourite(recipe))
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable(onClick = onCardClick)
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
                    .height(133.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = recipe.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(0.85f),
                    color = MaterialTheme.colorScheme.scrim
                )
                IconButton(
                    onClick = {
                        if (liked) {
                            recipesViewModel.deleteFavouriteRecipe(recipe)
                        } else {
                            recipesViewModel.addFavouriteRecipe(recipe)
                        }
                        liked = !liked
                    }, modifier = Modifier
                        .size(60.dp)
                        .weight(0.3f)
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = (if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder),
                        contentDescription = "favorite",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
