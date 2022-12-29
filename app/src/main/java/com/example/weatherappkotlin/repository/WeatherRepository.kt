package com.example.weatherappkotlin.repository

import android.util.Log
import com.example.weatherappkotlin.data.DataOrException
import com.example.weatherappkotlin.model.WeatherDailyResponse
import com.example.weatherappkotlin.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI) {
    suspend fun getWeather(city: String, units: String): DataOrException<WeatherDailyResponse, Boolean, Exception> {
        val response = try {
            api.getWeather(query = city, units = units)
        } catch (e: Exception) {
            Log.d("ERROR ->>", "GetWeather: $e")

            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}