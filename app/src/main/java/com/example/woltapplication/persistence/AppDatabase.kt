package com.example.woltapplication.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.woltapplication.data.Venue

@Database(entities = [Venue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun venueDao(): VenueDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: createDatabase(context)
        }

        private fun createDatabase(context: Context): AppDatabase {
            synchronized(this) { // Only one thread can enter here at a time
                if (INSTANCE == null) {
                    INSTANCE = buildRoomDB(context)
                }
                return INSTANCE!!
            }
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "wolt.db"
            ).build()
    }
}