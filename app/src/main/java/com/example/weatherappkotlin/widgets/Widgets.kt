package com.example.weatherappkotlin.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.model.WeatherObject
import com.example.weatherappkotlin.utils.formatDate
import com.example.weatherappkotlin.utils.formatDateTime
import com.example.weatherappkotlin.utils.formatDecimals

@Composable
fun WeatherDetailRow(weatherObject: WeatherObject) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherObject.weather.first().icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDate(weatherObject.dt).split(",").first(),
                modifier = Modifier.padding(5.dp)
            )

            WeatherStateImage(imageUrl = imageUrl, size = 60.dp)

            Surface(modifier = Modifier.padding(0.dp), shape = CircleShape, Color(0xFFFFC400)) {
                Text(
                    weatherObject.weather.first().description,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(weatherObject.temp.max) + "° ")
                }

                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append(formatDecimals(weatherObject.temp.min) + "°")
                }
            })
        }
    }
}

@Composable
fun RiseSunsetRow(weather: WeatherObject) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = formatDateTime(weather.sunrise), style = MaterialTheme.typography.caption)
        }

        Row() {
            Text(text = formatDateTime(weather.sunset), style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherObject, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.speed)} " + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String, size: Dp) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = null,
        modifier = Modifier.size(size)
    )
}