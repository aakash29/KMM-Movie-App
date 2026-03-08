package com.example.moviesapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val lang:String,
    val description: String,
    val image:String,
    val releaseDate:String,
)