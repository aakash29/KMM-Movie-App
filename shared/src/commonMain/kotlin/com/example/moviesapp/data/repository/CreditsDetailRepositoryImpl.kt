package com.example.moviesapp.data.repository

import com.example.moviesapp.core.AUTHORIZATION_BEARER_TOKEN
import com.example.moviesapp.core.BASE_URL
import com.example.moviesapp.core.Result
import com.example.moviesapp.data.MOVIE_DETAIL_CREDITS
import com.example.moviesapp.data.mapper.getDirector
import com.example.moviesapp.data.mapper.getWriter
import com.example.moviesapp.data.mapper.toCastDomain
import com.example.moviesapp.data.model.CreditDetailsResponse
import com.example.moviesapp.domain.model.Cast
import com.example.moviesapp.domain.repository.CreditsDetailRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom

class CreditsDetailRepositoryImpl(private val httpClient: HttpClient) : CreditsDetailRepository {

    override suspend fun getCredits(movieId: Int): Result<Pair<List<Cast>, Pair<String, String>>> {
        return try {
            val response = httpClient.get {
                headers {
                    endPoint(MOVIE_DETAIL_CREDITS.replace("{movie_id}", movieId.toString()))
                }
                contentType(ContentType.Application.Json)
            }
            if (response.status.isSuccess()) {
                val creditResponse = response.body<CreditDetailsResponse>()
                val cast = creditResponse.toCastDomain()
                val director = creditResponse.getDirector()
                val writer = creditResponse.getWriter()
                Result.Success(Pair(cast, Pair(director, writer)))
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