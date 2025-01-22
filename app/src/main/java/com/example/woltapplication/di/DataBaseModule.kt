package com.example.woltapplication.di

import android.content.Context
import com.example.woltapplication.persistence.AppDatabase
import com.example.woltapplication.persistence.VenueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideVenueDao(appDatabase: AppDatabase): VenueDao {
        return appDatabase.venueDao()
    }
}
