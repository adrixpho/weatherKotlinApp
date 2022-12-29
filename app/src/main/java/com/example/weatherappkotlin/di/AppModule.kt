package com.example.weatherappkotlin.di

import android.content.Context
import androidx.room.Room
import com.example.weatherappkotlin.data.WeatherDao
import com.example.weatherappkotlin.data.WeatherDatabase
import com.example.weatherappkotlin.network.WeatherAPI
import com.example.weatherappkotlin.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOpenWeatherAPI(): WeatherAPI {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_database")
            .fallbackToDestructiveMigration().build()
}