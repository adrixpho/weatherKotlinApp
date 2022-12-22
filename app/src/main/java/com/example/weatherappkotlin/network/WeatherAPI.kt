package com.example.weatherappkotlin.network

import com.example.weatherappkotlin.model.WeatherDailyResponse
import com.example.weatherappkotlin.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = Constants.API_KEY
    ): WeatherDailyResponse
}