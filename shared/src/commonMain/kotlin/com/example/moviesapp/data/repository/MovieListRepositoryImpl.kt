package com.example.moviesapp.data.repository

import com.example.moviesapp.core.AUTHORIZATION_BEARER_TOKEN
import com.example.moviesapp.core.BASE_URL
import com.example.moviesapp.core.Result
import com.example.moviesapp.data.MOVIE_LIST_PATH
import com.example.moviesapp.data.mapper.toDomain
import com.example.moviesapp.data.model.MoviesResponse
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieListRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom

class MovieListRepositoryImpl(
    private val httpClient: HttpClient
) : MovieListRepository {

    override suspend fun getMovieList(page: Int): Result<List<Movie>?> {
        return try {
            val response = httpClient.get {
                headers {
                    endPoint(MOVIE_LIST_PATH)
                    parameter("page", page)
                }
                contentType(ContentType.Application.Json)
            }
            if (response.status.isSuccess()) {
                Result.Success(response.body<MoviesResponse>().toDomain())
            } else {
                Result.Error(Exception("Error: ${response.status.value}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
            headers {
                append(
                    HttpHeaders.Authorization, AUTHORIZATION_BEARER_TOKEN
                )
            }
        }
    }
}