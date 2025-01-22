package com.example.woltapplication.network

import com.example.woltapplication.data.RestaurantData
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

    sealed class APIResult<out T> {
        data class Success<T>(val data: T) : APIResult<T>()
        data class Error(val message: String) : APIResult<Nothing>()
    }

    suspend fun getData(latitude: Double, longitude: Double): APIResult<RestaurantData> {
        val url = "https://restaurant-api.wolt.com/v1/pages/restaurants?lat=$latitude&lon=$longitude"
        return try {
            val response = client.get(url)
            APIResult.Success(response.body())
        } catch (e: Exception) {
            APIResult.Error("Error during API call: ${e.message}")
        }
    }
}

