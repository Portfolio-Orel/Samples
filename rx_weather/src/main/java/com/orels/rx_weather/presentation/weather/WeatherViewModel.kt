package com.orels.rx_weather.presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.rx_weather.domain.interactor.WeatherInteractor
import com.orels.rx_weather.domain.manager.CustomLocationManager
import com.orels.rx_weather.domain.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationManager: CustomLocationManager,
    private val weatherInteractor: WeatherInteractor
) : ViewModel() {
    var state by mutableStateOf(WeatherState())

    init {
        state = state.copy(isLoading = true)
        getWeather()
        getAddress()
    }

    fun onResume() {
        getWeather()
        getAddress()
    }

    private fun getAddress() {
        locationManager.getLocation()
            .flatMap { locationData ->
                locationManager.getAddress(
                    latitude = locationData.latitude ?: 0.0,
                    longitude = locationData.longitude ?: 0.0
                )
            }
            .subscribe({ city ->
                state = state.copy(city = city)
            }, {
                state = state.copy(isError = true)
            })
    }

    private fun getWeather() {
        locationManager.getLocation().flatMap {
            if (it.latitude != null && it.longitude != null) {
                return@flatMap weatherInteractor.getWeather(it.latitude, it.longitude)
            } else {
                return@flatMap Single.just(Weather())
            }
        }.subscribe({
            state = state.copy(
                isLoading = false, weather = it
            )
        }, {
            state = state.copy(
                isLoading = false, isError = true
            )
        })
    }
}