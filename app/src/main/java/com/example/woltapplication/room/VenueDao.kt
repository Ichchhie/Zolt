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
    suspend fun insertFavouriteVenue(venue: Venue)

    @Delete()
    suspend fun deleteFavouriteVenue(venue: Venue)

    @Query("SELECT * FROM venues WHERE isFavourite = 1")
    suspend fun getFavourites() : List<Venue>

    @Query("SELECT id FROM venues WHERE isFavourite = 1")
    suspend fun getFavouritesId() : List<String>
}