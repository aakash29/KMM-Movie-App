package com.example.moviesapp.domain.usecase

import com.example.moviesapp.core.Result
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieListRepository

class GetMovieListUseCase(private val repository: MovieListRepository) {

    suspend operator fun invoke(page: Int): Result<List<Movie>?> {
        return repository.getMovieList(page)
    }
}