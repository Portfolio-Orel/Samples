package com.orels.rx_weather.domain.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH).parse(this)
    } catch (e: java.text.ParseException) {
        null
    }
}