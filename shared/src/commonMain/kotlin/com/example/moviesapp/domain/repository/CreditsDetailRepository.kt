package com.example.moviesapp.domain.repository

import com.example.moviesapp.core.Result
import com.example.moviesapp.data.model.CreditDetailsResponse
import com.example.moviesapp.data.model.MovieDetailsResponse

interface CreditsDetailRepository {

    suspend fun getCreditsDetails(movieId: Int): Result<CreditDetailsResponse>
}