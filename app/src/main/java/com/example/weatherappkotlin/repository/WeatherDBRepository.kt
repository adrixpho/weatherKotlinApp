package com.example.weatherappkotlin.repository

import com.example.weatherappkotlin.data.WeatherDao
import com.example.weatherappkotlin.model.Favorite
import com.example.weatherappkotlin.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavoriteById(city = city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite = favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite = favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

}