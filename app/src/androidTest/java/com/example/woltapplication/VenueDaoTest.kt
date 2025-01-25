package com.example.woltapplication

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.woltapplication.data.Venue
import com.example.woltapplication.persistence.AppDatabase
import com.example.woltapplication.persistence.VenueDao
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class VenueDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var venueDao: VenueDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        venueDao = database.venueDao()
    }

    @After
    fun closeDatabase() {
        database.clearAllTables() // Clear tables after each test
        database.close()
    }

    @Test
    fun checkInsertVenueIsWorking() = runTest {
        val venue = Venue(
            id = "2",
            name = "Mc Donald's",
            shortDescription = "Burger and Fries",
            isFavourite = true
        )
        venueDao.insertFavouriteVenue(venue)
        val retrievedFavIds = venueDao.getFavouritesId()

        // Verify that the retrieved favouriteId matches the inserted one
        val containsInsertedId = retrievedFavIds.contains("2")
        assert(containsInsertedId == true)
    }

    @Test
    fun checkIfInsertVenueIsInsertingCorrectId() = runTest {
        val venue = Venue(
            id = "3",
            name = "Mc Donald's",
            shortDescription = "Burger and Fries",
            isFavourite = true
        )
        venueDao.insertFavouriteVenue(venue)
        val retrievedFavIds = venueDao.getFavouritesId()

        // Verify that the retrieved favouriteId matches the inserted one
        val containsInsertedId = retrievedFavIds.contains("5")
        assert(containsInsertedId == false)
    }

    @Test
    fun checkIfInsertVenueIsReplacingSameValue() = runTest {
        val venue = Venue(
            id = "2",
            name = "Mc Donald's",
            shortDescription = "Burger and Fries",
            isFavourite = true
        )
        venueDao.insertFavouriteVenue(venue)
        venueDao.insertFavouriteVenue(venue)
        venueDao.insertFavouriteVenue(venue)

        // Verify that the venue is inserted only once and replaced
        val containsInsertedId = venueDao.getVenueCountById("2")
        assert(containsInsertedId == 1)
    }

    @Test
    fun checkIfDeletedItemIsRemovedFromDB() = runTest {
        val venue = Venue(
            id = "5",
            name = "Mc Donald's",
            shortDescription = "Burger and Fries",
            isFavourite = true
        )
        venueDao.insertFavouriteVenue(venue)

        // Delete the venue
        venueDao.deleteFavouriteVenue(venue)
        // Verify that the deletion was successful
        val retrievedFavIds = venueDao.getFavouritesId()
        val containsInsertedId = retrievedFavIds.contains("5")
        assert(containsInsertedId == false)
    }
}