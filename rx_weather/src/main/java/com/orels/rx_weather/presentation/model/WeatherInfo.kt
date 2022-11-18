package com.orels.rx_weather.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.orels.rx_weather.R
import com.orels.rx_weather.domain.extension.getSafely
import com.orels.rx_weather.domain.model.Weather
import java.time.LocalDateTime
import java.time.ZoneOffset

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType
)

sealed class WeatherType(
    @StringRes val weatherDesc: Int, @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = R.string.clear_sky, iconRes = R.drawable.ic_sunny
    )

    object MainlyClear : WeatherType(
        weatherDesc = R.string.mainly_clear, iconRes = R.drawable.ic_cloudy
    )

    object PartlyCloudy : WeatherType(
        weatherDesc = R.string.partly_cloudy, iconRes = R.drawable.ic_cloudy
    )

    object Overcast : WeatherType(
        weatherDesc = R.string.overcast, iconRes = R.drawable.ic_cloudy
    )

    object Foggy : WeatherType(
        weatherDesc = R.string.foggy, iconRes = R.drawable.ic_very_cloudy
    )

    object DepositingRimeFog : WeatherType(
        weatherDesc = R.string.depositing_rime_fog, iconRes = R.drawable.ic_very_cloudy
    )

    object LightDrizzle : WeatherType(
        weatherDesc = R.string.light_drizzle, iconRes = R.drawable.ic_rainshower
    )

    object ModerateDrizzle : WeatherType(
        weatherDesc = R.string.moderate_drizzle, iconRes = R.drawable.ic_rainshower
    )

    object DenseDrizzle : WeatherType(
        weatherDesc = R.string.dense_drizzle, iconRes = R.drawable.ic_rainshower
    )

    object LightFreezingDrizzle : WeatherType(
        weatherDesc = R.string.slight_freezing_drizzle, iconRes = R.drawable.ic_snowyrainy
    )

    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = R.string.dense_freezing_drizzle, iconRes = R.drawable.ic_snowyrainy
    )

    object SlightRain : WeatherType(
        weatherDesc = R.string.slight_rain, iconRes = R.drawable.ic_rainy
    )

    object ModerateRain : WeatherType(
        weatherDesc = R.string.rainy, iconRes = R.drawable.ic_rainy
    )

    object HeavyRain : WeatherType(
        weatherDesc = R.string.heavy_rain, iconRes = R.drawable.ic_rainy
    )

    object HeavyFreezingRain : WeatherType(
        weatherDesc = R.string.heavy_freezing_rain, iconRes = R.drawable.ic_snowyrainy
    )

    object SlightSnowFall : WeatherType(
        weatherDesc = R.string.slight_snow_fall, iconRes = R.drawable.ic_snowy
    )

    object ModerateSnowFall : WeatherType(
        weatherDesc = R.string.moderate_snow_fall, iconRes = R.drawable.ic_heavysnow
    )

    object HeavySnowFall : WeatherType(
        weatherDesc = R.string.heavy_snow_fall, iconRes = R.drawable.ic_heavysnow
    )

    object SnowGrains : WeatherType(
        weatherDesc = R.string.snow_grains, iconRes = R.drawable.ic_heavysnow
    )

    object SlightRainShowers : WeatherType(
        weatherDesc = R.string.slight_rain_showers, iconRes = R.drawable.ic_rainshower
    )

    object ModerateRainShowers : WeatherType(
        weatherDesc = R.string.moderate_rain_showers, iconRes = R.drawable.ic_rainshower
    )

    object ViolentRainShowers : WeatherType(
        weatherDesc = R.string.violent_rain_showers, iconRes = R.drawable.ic_rainshower
    )

    object SlightSnowShowers : WeatherType(
        weatherDesc = R.string.light_snow_showers, iconRes = R.drawable.ic_snowy
    )

    object HeavySnowShowers : WeatherType(
        weatherDesc = R.string.heavy_snow_showers, iconRes = R.drawable.ic_snowy
    )

    object ModerateThunderstorm : WeatherType(
        weatherDesc = R.string.moderate_thunderstorm, iconRes = R.drawable.ic_thunder
    )

    object SlightHailThunderstorm : WeatherType(
        weatherDesc = R.string.thunderstorm_with_slight_hail, iconRes = R.drawable.ic_rainythunder
    )

    object HeavyHailThunderstorm : WeatherType(
        weatherDesc = R.string.thunderstorm_with_heavy_hail, iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}

private data class IndexedWeatherData(
    val index: Int, val data: WeatherData
)

fun Weather.toWeatherDataMap(): Map<Int, List<WeatherData>>? =
    hourly?.time?.mapIndexed { index, time ->
        val temperature = hourly?.temperatures?.getSafely(index) ?: 0.0
        val weatherCode = hourly?.weatherCodes?.getSafely(index) ?: 0
        val windSpeed = hourly?.windSpeeds?.getSafely(index) ?: 0.0
        val pressure = hourly?.pressures?.getSafely(index) ?: 0.0
        val humidity = hourly?.humidities?.getSafely(index) ?: 0.0
        IndexedWeatherData(
            index = index, data = WeatherData(
                time = LocalDateTime.ofEpochSecond((time?.time ?: 0) / 1000, 0, ZoneOffset.UTC),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }?.groupBy { indexedWeatherData ->
        indexedWeatherData.index / 24
    }?.mapValues {
        it.value.map { indexedWeatherData -> indexedWeatherData.data }
    }


fun Weather.toWeatherInfo(): WeatherInfo? {
    val weatherDataMap = toWeatherDataMap() ?: return null
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}