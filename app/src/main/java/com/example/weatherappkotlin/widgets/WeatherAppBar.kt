package com.example.weatherappkotlin.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherappkotlin.model.Favorite
import com.example.weatherappkotlin.navigation.WeatherScreens
import com.example.weatherappkotlin.screens.favorite.FavoriteViewModel

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = false,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(title = {
        Text(
            text = title,
            color = MaterialTheme.colors.onSecondary,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
        )
    }, navigationIcon = {
        if (icon != null) {
            Icon(imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier.clickable { onButtonClicked.invoke() })
        }

        if (isMainScreen) {
            val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value.filter { item ->
                item.city == title.split(",").first()
            }

            if (isAlreadyFavList.isNullOrEmpty()) {
                Icon(imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .scale(0.9f)
                        .padding(start = 12.dp)
                        .clickable {
                            val dataCity = title.split(",")

                            favoriteViewModel
                                .insertFavorite(
                                    Favorite(
                                        city = dataCity.first(),
                                        country = dataCity.getOrNull(1) ?: ""
                                    )
                                )
                                .run { showIt.value = true }
                        })
            } else {
                showIt.value = false
                Box {}
            }

            ShowToast(context = context, showIt)
        }
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = { onAddActionClicked.invoke() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
            IconButton(onClick = { showDialog.value = true }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
            }
        } else Box {

        }
    }, backgroundColor = Color.Transparent, elevation = elevation
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "City Added to Favorites", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { _, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        }, contentDescription = null, tint = Color.LightGray
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = text, modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavoriteScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                            )
                        }, fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}
