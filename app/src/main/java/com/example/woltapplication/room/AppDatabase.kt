package com.example.woltapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.woltapplication.data.Venue
import kotlin.reflect.KParameter

@Database(entities = [Venue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun venueDao(): VenueDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "wolt.db"
            ).build()
    }
}