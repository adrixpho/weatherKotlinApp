package com.example.weatherappkotlin.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappkotlin.model.Favorite
import com.example.weatherappkotlin.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getFavorites().distinctUntilChanged().collect {
                if (it.isNullOrEmpty()) {
                    Log.d("TAG", "Favorites Empty")
                } else {
                    Log.d("FAVS", ":$it")
                    _favList.value = it
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch { weatherDBRepository.insertFavorite(favorite = favorite) }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch { weatherDBRepository.deleteFavorite(favorite = favorite) }

    fun deleteAllFavorites() = viewModelScope.launch { weatherDBRepository.deleteAllFavorites() }


}