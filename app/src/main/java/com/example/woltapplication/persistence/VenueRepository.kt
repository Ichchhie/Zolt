package com.example.woltapplication.persistence

import com.example.woltapplication.data.Venue
import com.example.woltapplication.room.VenueDao
import javax.inject.Inject

class VenueRepository @Inject constructor(private val venueDao: VenueDao) {
    suspend fun getAllFavouriteVenues() : List<Venue>{
        return venueDao.getFavourites()
    }
    suspend fun getFavouriteVenuesIds() : List<String>{
        return venueDao.getFavouritesId()
    }
    suspend fun insertVenue(venue: Venue){
        venueDao.insertFavouriteVenue(venue)
    }

    suspend fun deleteVenue(venue: Venue){
        venueDao.deleteFavouriteVenue(venue)
    }
}