package com.example.woltapplication.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.woltapplication.data.Venue

@Dao
interface VenueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteVenue(venue: Venue)

    @Delete()
    fun deleteFavouriteVenue(venue: Venue)

    @Query("SELECT * FROM venues WHERE isFavourite = 1")
    fun getFavourites() : List<Venue>

    @Query("SELECT id FROM venues WHERE isFavourite = 1")
    fun getFavouritesId() : List<String>
}