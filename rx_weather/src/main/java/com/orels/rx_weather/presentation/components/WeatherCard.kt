package com.orels.rx_weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orels.rx_weather.presentation.model.WeatherInfo
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import com.orels.rx_weather.R


@Composable
fun WeatherCard(
    weatherInfo: WeatherInfo?,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    weatherInfo?.currentWeatherData?.let { data ->
        Box(
            modifier = modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Today ${
                        data.time.format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                    }",
                    modifier = Modifier.align(Alignment.End),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}°C",
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = data.weatherType.weatherDesc),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = Unit.HPA,
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                        iconTint = MaterialTheme.colorScheme.onBackground,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                    )
                    WeatherDataDisplay(
                        value = data.humidity.roundToInt(),
                        unit = Unit.Percentage,
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        iconTint = MaterialTheme.colorScheme.onBackground,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                    )
                    WeatherDataDisplay(
                        value = data.windSpeed.roundToInt(),
                        unit = Unit.KMH,
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        iconTint = MaterialTheme.colorScheme.onBackground,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        }
    }
}