package com.example.weatherappkotlin.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappkotlin.data.DataOrException
import com.example.weatherappkotlin.model.WeatherDailyResponse
import com.example.weatherappkotlin.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String): DataOrException<WeatherDailyResponse, Boolean, Exception> {
        return repository.getWeather(city)
    }
}