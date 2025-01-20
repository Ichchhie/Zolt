package com.example.woltapplication.persistence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woltapplication.api.ApiService
import com.example.woltapplication.data.RestaurantData
import com.example.woltapplication.data.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val venueRepository: VenueRepository) :
    ViewModel() {
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

    sealed class UiState {
        data object Loading : UiState() // Used only for the initial loading
        data class Success(val data: RestaurantData) : UiState()
        data class LoadingWithData(val data: RestaurantData) :
            UiState() // Loading while displaying old data

        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    // Use MutableStateFlow to hold the favourite venue IDs
    private val _favouriteVenuesIds = MutableStateFlow<List<String>>(emptyList())
    val favouriteVenuesIds: StateFlow<List<String>> get() = _favouriteVenuesIds

    init {
        // Start fetching data on ViewModel initialization
        startFetchingData()
    }

    private fun startFetchingData() {
        viewModelScope.launch {
            while (true) {
                // Get the current location
                val currentLocation = locationCoordinates[currentIndex]
                // Fetch the data from API
                fetchRestaurantData(currentLocation.first, currentLocation.second)
                // Update the index to the next location coordinates
                currentIndex = (currentIndex + 1) % locationCoordinates.size
                // Delay for 10 seconds before updating again
                delay(10000)  // 10 seconds
            }
        }
    }

    private fun fetchRestaurantData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            // Check if there is existing data to show while loading
            val existingData = (_uiState.value as? UiState.Success)?.data

            if (existingData != null) {
                // Emit LoadingWithData state
                _uiState.value = UiState.LoadingWithData(existingData)
            } else {
                // Emit regular Loading state
                _uiState.value = UiState.Loading
            }

            // Fetch data from API
            when (val result = apiService.getData(latitude, longitude)) {
                is ApiService.APIResult.Success -> _uiState.value = UiState.Success(result.data)
                is ApiService.APIResult.Error -> {
                    // Show error, but keep old data if available
                    if (existingData != null) {
                        _uiState.value = UiState.LoadingWithData(existingData)
                    } else {
                        _uiState.value = UiState.Error(result.message)
                    }
                }
            }
        }
    }

    fun getFavouriteVenues() {
        viewModelScope.launch {
            venueRepository.getAllFavouriteVenues()
        }
    }

    // Fetch the favourite venues IDs and update the StateFlow
    fun getFavouriteVenuesIDs() {
        viewModelScope.launch {
            val ids = venueRepository.getFavouriteVenuesIds() // This should return List<String>
            _favouriteVenuesIds.value = ids
        }
    }

    fun insertVenue(venue: Venue) {
        viewModelScope.launch {
            venueRepository.insertVenue(venue)
            getFavouriteVenuesIDs()
        }
    }

    fun deletedVenue(venue: Venue) {
        viewModelScope.launch {
            venueRepository.deleteVenue(venue)
            getFavouriteVenuesIDs()
        }
    }
}
