package com.rajendra.dailypulsekmp.data.network

import com.rajendra.dailypulsekmp.httpClientEngineFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol

fun createHttpClient(): HttpClient { // No need to pass factory if using the expect fun directly
    return HttpClient(httpClientEngineFactory()) { // Call the expect fun here
        // JSON Serialization
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues=true
                explicitNulls=true
            })
        }

        // Logging
        install(Logging) {
            // logger = Logger.DEFAULT // You can customize logger if needed
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("API Log:"+message) // Check Logcat for "KTOR HTTP LOG"
                }
            }
        }

        // Default request configuration
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "newsapi.org" // Your base host
            }
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            // Add API Key securely (e.g., via an interceptor or passed through)
        }
        // ... other configurations ...
    }
}
