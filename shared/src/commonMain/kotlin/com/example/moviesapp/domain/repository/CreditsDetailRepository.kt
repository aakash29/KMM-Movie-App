package com.example.moviesapp.domain.repository

import com.example.moviesapp.core.Result
import com.example.moviesapp.domain.model.Cast

interface CreditsDetailRepository {

    suspend fun getCredits(movieId: Int): Result<Pair<List<Cast>, Pair<String, String>>>
}