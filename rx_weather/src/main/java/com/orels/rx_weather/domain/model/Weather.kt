package com.orels.rx_weather.domain.model

import java.util.*

data class Weather(
    var latitude: Double? = null,
    var longitude: Double? = null,
    var generationtimeMs: Double? = null,
    var utcOffsetSeconds: Int? = null,
    var timezone: String? = null,
    var timezoneAbbreviation: String? = null,
    var elevation: Int? = null,
    var hourlyUnits: HourlyUnits? = HourlyUnits(),
    var hourly: Hourly? = Hourly()
)

data class Hourly(
    var time: ArrayList<Date?> = arrayListOf(),
    var temperatures: ArrayList<Double> = arrayListOf(),
    var weatherCodes: ArrayList<Int> = arrayListOf(),
    var humidities: ArrayList<Double> = arrayListOf(),
    var windSpeeds: ArrayList<Double> = arrayListOf(),
    var pressures: ArrayList<Double> = arrayListOf()
) {
    val timeSortedDescending: List<Date?>
        get() = time.sortedByDescending { it }

}

data class HourlyUnits(
    var time: Date? = null,
    var temperature2m: String? = null,
    var weathercode: String? = null,
    var relativehumidity2m: String? = null,
    var windspeed10m: String? = null,
    var pressureMsl: String? = null
)