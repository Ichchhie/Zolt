package com.example.woltapplication.persistence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woltapplication.data.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueViewModel @Inject constructor(private val venueRepository: VenueRepository) :
    ViewModel() {

    // Use MutableStateFlow to hold the favourite venue IDs
    private val _favouriteVenuesIds = MutableStateFlow<List<String>>(emptyList())
    val favouriteVenuesIds: StateFlow<List<String>> get() = _favouriteVenuesIds

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

    fun deleteVenue(venue: Venue) {
        viewModelScope.launch {
            venueRepository.deleteVenue(venue)
            getFavouriteVenuesIDs()
        }
    }
}
