package com.example.weatherappkotlin.screens.main

import androidx.lifecycle.ViewModel
import com.example.weatherappkotlin.data.DataOrException
import com.example.weatherappkotlin.model.WeatherDailyResponse
import com.example.weatherappkotlin.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String, units: String): DataOrException<WeatherDailyResponse, Boolean, Exception> {
        return repository.getWeather(city = city, units = units)
    }
}