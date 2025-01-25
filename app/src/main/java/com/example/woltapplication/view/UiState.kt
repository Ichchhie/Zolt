package com.example.woltapplication.view

import com.example.woltapplication.data.RestaurantData

sealed class UiState {
    data object Loading : UiState() // Used only for the initial loading
    data class Success(val data: RestaurantData) : UiState()
    data class LoadingWithData(val data: RestaurantData) :
        UiState() // Show Loading while displaying old data to not make the screen blank whole loading

    data class Error(val message: String) : UiState()
}