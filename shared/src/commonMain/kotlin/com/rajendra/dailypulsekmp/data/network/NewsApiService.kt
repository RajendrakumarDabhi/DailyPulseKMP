package com.rajendra.dailypulsekmp.data.network

import com.rajendra.dailypulsekmp.domain.model.NewsApiResponse
import com.rajendra.dailypulsekmp.utils.Constants
import com.rajendra.dailypulsekmp.utils.Constants.PUBLISHED_AT
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


class NewsApiService(private val httpClient: HttpClient) { // <<-- HttpClient is INJECTED here

    suspend fun getEverythingWithQuery(
        query: String = "news",
        fromDate: String="2025-07-19"
    ): Result<NewsApiResponse> {
        return try {
            // httpClient is used here
            val response: NewsApiResponse =
                httpClient.get("${Constants.BASE_URL}${Constants.ENDPOINT_TOP_HEADLINES}")
                {
                    parameter(Constants.API_KEY, Constants.KEY)
                    parameter(Constants.QUERY, query)
                    parameter(Constants.FROM_DATE, fromDate)
                    parameter(Constants.SHORT_BY, PUBLISHED_AT)
                }.body()
            Result.success(response)
        } catch (e: Exception) {
            println("Error fetching top headlines: ${e.message}\n${e.stackTraceToString()}")
            Result.failure(e)
        }
    }
}