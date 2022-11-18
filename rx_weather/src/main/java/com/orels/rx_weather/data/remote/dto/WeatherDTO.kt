package com.orels.rx_weather.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.orels.rx_weather.domain.extension.toDate
import com.orels.rx_weather.domain.model.Hourly
import com.orels.rx_weather.domain.model.HourlyUnits
import com.orels.rx_weather.domain.model.Weather
import java.util.*

data class WeatherDTO(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("generationtime_ms") var generationtimeMs: Double? = null,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("hourly_units") var hourlyUnits: HourlyUnitsDTO? = HourlyUnitsDTO(),
    @SerializedName("hourly") var hourly: HourlyDTO? = HourlyDTO()
) {
    fun toWeather(): Weather = Weather(
        latitude = latitude,
        longitude = longitude,
        generationtimeMs = generationtimeMs,
        utcOffsetSeconds = utcOffsetSeconds,
        timezone = timezone,
        timezoneAbbreviation = timezoneAbbreviation,
        elevation = elevation,
        hourlyUnits = hourlyUnits?.toHourlyUnits(),
        hourly = hourly?.toHourly()
    )
}

data class HourlyDTO(
    @SerializedName("time") var time: ArrayList<String> = arrayListOf(),
    @SerializedName("temperature_2m") var temperature2m: ArrayList<Double> = arrayListOf(),
    @SerializedName("weathercode") var weathercode: ArrayList<Int> = arrayListOf(),
    @SerializedName("relativehumidity_2m") var relativehumidity2m: ArrayList<Double> = arrayListOf(),
    @SerializedName("windspeed_10m") var windspeed10m: ArrayList<Double> = arrayListOf(),
    @SerializedName("pressure_msl") var pressureMsl: ArrayList<Double> = arrayListOf()
) {
    fun toHourly(): Hourly = Hourly(
        time = ArrayList(time.map { it.toDate() }),
        temperatures = temperature2m,
        weatherCodes = weathercode,
        humidities = relativehumidity2m,
        windSpeeds = windspeed10m,
        pressures = pressureMsl
    )
}

data class HourlyUnitsDTO(
    @SerializedName("time") var time: String? = null,
    @SerializedName("temperature_2m") var temperature2m: String? = null,
    @SerializedName("weathercode") var weathercode: String? = null,
    @SerializedName("relativehumidity_2m") var relativehumidity2m: String? = null,
    @SerializedName("windspeed_10m") var windspeed10m: String? = null,
    @SerializedName("pressure_msl") var pressureMsl: String? = null
) {
    fun toHourlyUnits(): HourlyUnits = HourlyUnits(
        time = time?.toDate(),
        temperature2m = temperature2m,
        weathercode = weathercode,
        relativehumidity2m = relativehumidity2m,
        windspeed10m = windspeed10m,
        pressureMsl = pressureMsl
    )
}