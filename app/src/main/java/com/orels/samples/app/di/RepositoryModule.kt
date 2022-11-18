package com.orels.samples.app.di

import com.orels.rx_weather.data.remote.interactor.WeatherInteractorImpl
import com.orels.rx_weather.domain.interactor.WeatherInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWeatherInteractor(weatherInteractor: com.orels.rx_weather.data.remote.interactor.WeatherInteractorImpl): com.orels.rx_weather.domain.interactor.WeatherInteractor
}