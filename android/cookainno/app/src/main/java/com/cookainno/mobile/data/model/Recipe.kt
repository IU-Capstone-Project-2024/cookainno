package com.cookainno.mobile.data.model

data class Recipe(
    val id: Int,
    val name: String,
    val instructions: String,
    val ingredients: String,
    val likes: Int,
    val imageUrl: String
)
