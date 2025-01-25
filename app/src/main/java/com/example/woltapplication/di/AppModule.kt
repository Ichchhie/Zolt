package com.example.woltapplication.di

import android.content.Context
import com.example.woltapplication.utils.NetworkHelper
import com.example.woltapplication.utils.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Provides
    @Singleton
    fun provideStringProvider(context: Context): StringProvider {
        return StringProvider(context)
    }
}
