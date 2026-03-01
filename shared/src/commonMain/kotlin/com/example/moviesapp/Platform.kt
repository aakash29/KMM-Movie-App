package com.example.moviesapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform