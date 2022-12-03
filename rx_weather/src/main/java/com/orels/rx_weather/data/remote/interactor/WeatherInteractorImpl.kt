package com.orels.rx_weather.data.remote.interactor

import com.orels.rx_weather.data.remote.WeatherAPI
import com.orels.rx_weather.domain.interactor.WeatherInteractor
import com.orels.rx_weather.domain.model.Weather
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherInteractorImpl @Inject constructor(private val weatherApi: WeatherAPI) : WeatherInteractor {
    override fun getWeather(latitude: Double, longitude: Double): Single<Weather> =
        weatherApi.getWeather(latitude = latitude, longitude = longitude)
            .subscribeOn(Schedulers.io())
            .map { it.toWeather() }
}