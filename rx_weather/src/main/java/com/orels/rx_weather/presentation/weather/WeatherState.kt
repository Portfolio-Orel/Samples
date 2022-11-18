package com.orels.rx_weather.presentation.weather

import com.orels.rx_weather.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,

    val city: String = "",
    val weather: Weather? = null,
)