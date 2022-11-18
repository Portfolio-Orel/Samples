package com.orels.rx_weather.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.components.Loading
import com.orels.components.OnLifecycleEvent
import com.orels.rx_weather.presentation.components.WeatherCard
import com.orels.rx_weather.presentation.components.WeatherForecast
import com.orels.rx_weather.presentation.model.toWeatherInfo

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state
    OnLifecycleEvent(onResume = viewModel::onResume)

    if (state.isLoading) {
        Loading(color = MaterialTheme.colorScheme.primary, size = 12.dp)
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                WeatherCard(
                    weatherInfo = state.weather?.toWeatherInfo(),
                    backgroundColor = MaterialTheme.colorScheme.secondary

                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecast(weatherInfo = state.weather?.toWeatherInfo())
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.city,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            if (state.isError) {
                Text(
                    text = "Error",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

