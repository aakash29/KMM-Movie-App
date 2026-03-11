package com.example.moviesapp.domain.usecase

import com.example.moviesapp.core.Result
import com.example.moviesapp.domain.model.MovieDetails
import com.example.moviesapp.domain.repository.CreditsDetailRepository
import com.example.moviesapp.domain.repository.MovieDetailRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

class GetMovieDetailUseCase(
    private val repository: MovieDetailRepository,
    private val creditRepository: CreditsDetailRepository
) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {

        return supervisorScope {

            val movieDetailDeferred = async { repository.getMovieDetails(movieId) }
            val creditDetailDeferred = async { creditRepository.getCredits(movieId) }

            val movieDetailResult = movieDetailDeferred.await()
            val creditDetailResult = creditDetailDeferred.await()

            if (movieDetailResult is Result.Success && creditDetailResult is Result.Success) {
                val movieDetails = movieDetailResult.data
                val (cast, crew) = creditDetailResult.data
                val (director, writer) = crew

                Result.Success(
                    movieDetails.copy(
                        cast = cast,
                        director = director,
                        writer = writer
                    )
                )
            } else {
                Result.Error(Exception("Failed to fetch movie details"))
            }
        }
    }
}