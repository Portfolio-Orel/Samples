package com.orels.rx_weather.data.remote

import com.orels.rx_weather.data.remote.dto.WeatherDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    fun getWeather(
        @Query("latitude") latitude: Double = 52.52,
        @Query("longitude") longitude: Double = 13.41,
    ): Single<WeatherDTO>
}
