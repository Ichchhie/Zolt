package com.example.woltapplication.persistence

import com.example.woltapplication.data.Venue
import com.example.woltapplication.room.VenueDao
import javax.inject.Inject

class VenueRepository @Inject constructor(private val venueDao: VenueDao) {
    fun getAllFavouriteVenues() : List<Venue>{
        return venueDao.getFavourites()
    }
    fun getFavouriteVenuesIds() : List<String>{
        return venueDao.getFavouritesId()
    }
    fun insertVenue(venue: Venue){
        venueDao.insertFavouriteVenue(venue)
    }

    fun deleteVenue(venue: Venue){
        venueDao.deleteFavouriteVenue(venue)
    }
}