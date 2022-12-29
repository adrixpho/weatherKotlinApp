package com.example.weatherappkotlin.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherappkotlin.data.DataOrException
import com.example.weatherappkotlin.model.WeatherDailyResponse
import com.example.weatherappkotlin.model.WeatherObject
import com.example.weatherappkotlin.navigation.WeatherScreens
import com.example.weatherappkotlin.screens.settings.SettingsViewModel
import com.example.weatherappkotlin.utils.formatDate
import com.example.weatherappkotlin.utils.formatDecimals
import com.example.weatherappkotlin.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb.first().unit.split(" ").first().lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<WeatherDailyResponse, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = curCity, units = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainContent(
                data = weatherData.data!!,
                navController = navController,
                isImperial = isImperial
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent(data: WeatherDailyResponse, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = data.city.name + ", ${data.city.country}",
            isMainScreen = true,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        Content(data = data, isImperial = isImperial)
    }
}

@Composable
fun Content(data: WeatherDailyResponse, isImperial: Boolean) {
    val weatherItem = data.list.first()
    val imageUrl = "https://openweathermap.org/img/wn/${data.list.first().weather.first().icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl, size = 80.dp)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather.first().main, fontStyle = FontStyle.Italic)
            }
        }

        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)

        Divider()

        RiseSunsetRow(weather = weatherItem)

        Text(
            text = "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
                items(items = data.list) { item: WeatherObject ->
                    WeatherDetailRow(weatherObject = item)
                }
            }
        }
    }
}


