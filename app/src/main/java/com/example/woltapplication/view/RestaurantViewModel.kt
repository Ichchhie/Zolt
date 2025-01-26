package com.example.woltapplication.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woltapplication.R
import com.example.woltapplication.network.APIResult
import com.example.woltapplication.network.ApiService
import com.example.woltapplication.utils.NetworkHelper
import com.example.woltapplication.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val apiService = ApiService()
    // Location coordinates for mocking the location change
    private val locationCoordinates = listOf(
        Pair(60.169418, 24.931618),
        Pair(60.169818, 24.932906),
        Pair(60.170005, 24.935105),
        Pair(60.169108, 24.936210),
        Pair(60.168355, 24.934869),
        Pair(60.167560, 24.932562),
        Pair(60.168254, 24.931532),
        Pair(60.169012, 24.930341),
        Pair(60.170085, 24.929569)
    )
    private var currentIndex = 0

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        startFetchingData()
    }

    fun startFetchingData() {
        viewModelScope.launch {
            while (true) {
                val currentLocation = locationCoordinates[currentIndex]
                fetchRestaurantData(currentLocation.first, currentLocation.second)
                // Update the index to the next location coordinates
                currentIndex = (currentIndex + 1) % locationCoordinates.size
                // Delay for 10 seconds before updating again
                delay(10000)
            }
        }
    }

    private fun fetchRestaurantData(latitude: Double, longitude: Double) {
        Log.d("apple", "fetchRestaurantData: "+latitude+longitude)
        viewModelScope.launch {
            // Check if there is existing data to show while loading
            val existingData = (_uiState.value as? UiState.Success)?.data

            if (existingData != null) {
                _uiState.value = UiState.LoadingWithData(existingData)
            } else {
                _uiState.value = UiState.Loading
            }

            if (networkHelper.isNetworkAvailable()) {
                // Fetch data from API
                when (val result = apiService.getRestaurantsFromAPI(latitude, longitude)) {
                    is APIResult.Success -> _uiState.value = UiState.Success(result.data)
                    is APIResult.Error -> {
                        // Show error, but keep old data if available
                        if (existingData != null) {
                            _uiState.value = UiState.LoadingWithData(existingData)
                        } else {
                            _uiState.value = UiState.Error(result.message)
                        }
                    }
                }
            } else {
                // Show error if no internet connection
                val errorMessage = stringProvider.getString(R.string.no_internet_connection)
                _uiState.value = UiState.Error(errorMessage)
            }
        }
    }

    fun setUiState(newState: UiState) {
        _uiState.value = newState
    }
}