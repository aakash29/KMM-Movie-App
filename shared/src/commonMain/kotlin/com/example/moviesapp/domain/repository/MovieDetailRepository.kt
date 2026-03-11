package com.example.moviesapp.domain.repository

import com.example.moviesapp.core.Result
import com.example.moviesapp.domain.model.MovieDetails

interface MovieDetailRepository {

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
}