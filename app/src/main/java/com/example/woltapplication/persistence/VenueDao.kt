package com.example.woltapplication.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.woltapplication.data.Venue

@Dao
interface VenueDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertFavouriteVenue(venue: Venue)

    @Delete()
    suspend fun deleteFavouriteVenue(venue: Venue)

    @Query("SELECT id FROM venues")
    suspend fun getFavouritesId() : List<String>

    @Query("SELECT COUNT(*) FROM venues WHERE id = :id")
    suspend fun getVenueCountById(id: String): Int
}