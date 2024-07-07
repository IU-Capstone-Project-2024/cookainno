package com.cookainno.mobile.ui.screens.favourites

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.recipes.RecipeItem
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel
import com.cookainno.mobile.ui.screens.recipes.TopBar
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouritesScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val favouriteRecipes by recipesViewModel.favouriteRecipes.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val isFavouriteRefreshing by recipesViewModel.isFavouriteRefreshing.collectAsState()
    var isSearchingState by rememberSaveable {
        mutableStateOf(false)
    }

    val state = rememberPullToRefreshState()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        recipesViewModel.getFavouriteRecipes()
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastIndex ->
                if (lastIndex != null && lastIndex >= (favouriteRecipes?.size ?: 0) - 1) {
                    if (!isSearchingState) {
                        recipesViewModel.getFavouriteRecipes()
                    } else {
                        recipesViewModel.searchFavouriteRecipes(searchQuery.text)
                    }
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            isMain = false,
            isName = false,
            shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
            query = searchQuery,
            navController = navController,
            onQueryChanged = { searchQuery = it },
            onSearchClick = {
                isSearchingState = true
                recipesViewModel.resetFavouritePagination()
                recipesViewModel.searchFavouriteRecipes(searchQuery.text)
            }
        )
        PullToRefreshBox(
            state = state,
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth(),
            onRefresh = {
                if (!isSearchingState) {
                    recipesViewModel.resetFavouritePagination()
                    recipesViewModel.getFavouriteRecipes()
                }
            },
            isRefreshing = isFavouriteRefreshing
        ) {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)
            ) {
                items(favouriteRecipes ?: emptyList()) { recipe ->
                    RecipeItem(recipe = recipe, recipesViewModel = recipesViewModel, onCardClick = {
                        navController.navigate(NavRoutes.DETAILS.name)
                    })
                }
            }
        }
    }
}
