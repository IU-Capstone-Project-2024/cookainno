package com.cookainno.mobile.data.model

import com.google.gson.annotations.SerializedName

data class GenRecipesResponse(
    @SerializedName("recipes") val recipes: List<GeneratedRecipe>
)

data class GeneratedRecipe(
    @SerializedName("name") val name: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instruction") val instructions: String,
    var imageUrl: String
)

data class RecipeToAdd(
    val name: String,
    val ingredients: String,
    val instructions: String,
    val imageUrl: String
)
