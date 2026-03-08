package com.example.moviesapp.domain.usecase

import com.example.moviesapp.core.IMAGE_BASE_URL
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

            val movieDetailResult = async { repository.getMovieDetails(movieId) }
            val creditDetailResult = async { creditRepository.getCreditsDetails(movieId) }

            val movieDetail = movieDetailResult.await()
            val creditDetail = creditDetailResult.await()

            if (movieDetail is Result.Success && creditDetail is Result.Success) {
                val movieDetails = movieDetail.data
                val creditDetails = creditDetail.data

                Result.Success(
                    MovieDetails(
                        id = movieDetails.id ?: 0,
                        title = movieDetails.title.orEmpty(),
                        description = movieDetails.overview.orEmpty(),
                        voteAverage = movieDetails.voteAverage ?: 0.0,
                        tagLine = movieDetails.tagline.orEmpty(),
                        image = IMAGE_BASE_URL + movieDetails.backdropPath.orEmpty(),
                        director = creditDetails.crew?.find { it?.job == "Director" }?.job.orEmpty(),
                        writer = creditDetails.crew?.find { it?.job == "Writer" }?.job.orEmpty(),
                        cast = creditDetails.cast?.joinToString(", ") { it?.name.orEmpty() }
                            .orEmpty(),
                        genres = movieDetails.genres?.joinToString(", ") { it?.name.orEmpty() }
                            .orEmpty(),
                        budget = movieDetails.budget ?: 0,
                        revenue = movieDetails.revenue ?: 0,
                        releaseDate = movieDetails.releaseDate.orEmpty(),
                        status = movieDetails.status.orEmpty(),
                        runtime = movieDetails.runtime ?: 0
                    )
                )
            } else {
                Result.Error(Exception("Failed to fetch movie details"))
            }
        }
    }
}