package com.orels.rx_weather.domain.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class CustomLocationManager @Inject constructor(@ApplicationContext private val context: Context) {

    private lateinit var locationManager: LocationManager

    fun getLocation(): Single<LocationData> {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Single.just(LocationData())
        }
        return Single.fromCallable {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                return@fromCallable LocationData(latitude = it.latitude, longitude = it.longitude)
            }
        }
    }

    /**
     * Get the city name from the coordinates
     */
    fun getAddress(latitude: Double, longitude: Double): Single<String> {
        val geocoder = Geocoder(context, Locale.getDefault())
        return Single.fromCallable {
            var address: Address? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    address = addresses.firstOrNull()
                }
            } else {
                @Suppress("DEPRECATION")
                address = geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()
            }
            return@fromCallable address?.locality ?: ""
        }
    }
}

data class LocationData(val latitude: Double? = null, val longitude: Double? = null)