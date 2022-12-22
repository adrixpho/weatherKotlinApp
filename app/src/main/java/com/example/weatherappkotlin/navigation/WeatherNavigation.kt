package com.example.weatherappkotlin.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherappkotlin.screens.main.MainScreen
import com.example.weatherappkotlin.screens.main.MainViewModel
import com.example.weatherappkotlin.screens.splash.WeatherSplashScreen

//This contains the nav controller
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    //Place where we are going to put all the screens ( Host all the navigation )
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }
    }
}