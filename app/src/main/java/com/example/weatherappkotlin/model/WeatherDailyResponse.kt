package com.example.weatherappkotlin.model

data class WeatherDailyResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherObject>,
    val message: Double
)