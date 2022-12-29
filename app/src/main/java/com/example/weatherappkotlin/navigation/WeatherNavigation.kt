package com.example.weatherappkotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherappkotlin.screens.about.AboutScreen
import com.example.weatherappkotlin.screens.favorite.FavoriteScreen
import com.example.weatherappkotlin.screens.main.MainScreen
import com.example.weatherappkotlin.screens.search.SearchScreen
import com.example.weatherappkotlin.screens.settings.SettingsScreen
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

        val parameterName = "city"
        val mainRoute = WeatherScreens.MainScreen.name
        composable(
            "$mainRoute/{$parameterName}",
            arguments = listOf(navArgument(parameterName) { type = NavType.StringType })
        ) { navBack ->
            navBack.arguments?.getString(parameterName).let { city ->
                MainScreen(navController = navController, city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}