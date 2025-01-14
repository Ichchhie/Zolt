package com.example.woltapplication.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
//            json() // this gave the error of illegal input so below one was necessary to be used
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            level = LogLevel.BODY
            level = LogLevel.ALL
        }
    }

    suspend fun getData(): RestaurantData? {
        val url = "https://restaurant-api.wolt.com/v1/pages/restaurants?lat=60.170187&lon=24.930599"
        try {
            val response = client.get(url)
            Log.d("apple", "HTTP Status: ${response.status}")
//            Log.d("apple", "Response Body: ${response.bodyAsText()}")
            return response.body() // Deserialize to ApiResponse
        } catch (e: Exception) {
            Log.d("apple", "Error during API call: ${e.message}")
            return null
        }
    }
}

