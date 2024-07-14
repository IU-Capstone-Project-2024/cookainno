package com.cookainno.mobile.data.model

data class Recipe(
    val id: Int,
    val name: String,
    val instructions: String,
    val ingredients: String,
    var likes: Int,
    val imageUrl: String
)
