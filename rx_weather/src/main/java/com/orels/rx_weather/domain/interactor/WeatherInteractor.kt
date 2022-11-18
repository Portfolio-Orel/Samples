package com.orels.rx_weather.domain.interactor

import com.orels.rx_weather.domain.model.Weather
import io.reactivex.rxjava3.core.Single

interface WeatherInteractor {
    fun getWeather(latitude: Double, longitude: Double): Single<Weather>
}