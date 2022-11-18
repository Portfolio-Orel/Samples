package com.orels.rx_weather.domain.extension

fun <T> List<T>.getSafely(index: Int): T? {
    return if (index < size) {
        get(index)
    } else {
        null
    }
}