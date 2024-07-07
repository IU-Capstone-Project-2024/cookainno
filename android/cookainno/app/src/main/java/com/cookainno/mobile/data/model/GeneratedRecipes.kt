package com.cookainno.mobile.data.model

import com.google.gson.annotations.SerializedName

data class GenRecipesResponse(
    @SerializedName("recipes") val recipes: List<GeneratedRecipes>
)

data class GeneratedRecipes(
    @SerializedName("name") val name: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instruction") val instruction: String
)
