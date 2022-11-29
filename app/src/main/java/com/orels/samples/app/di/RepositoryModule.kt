package com.orels.samples.app.di

import com.orels.rx_weather.data.remote.interactor.WeatherInteractorImpl
import com.orels.rx_weather.domain.interactor.WeatherInteractor
import com.orels.samples.todo.data.interactor.TodoInteractorImpl
import com.orels.samples.todo.data.remote.TodoRepositoryImpl
import com.orels.samples.todo.domain.interactor.TodoInteractor
import com.orels.samples.todo.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideWeatherInteractor(weatherInteractor: WeatherInteractorImpl): WeatherInteractor


    // _TODO
    @Binds
    @Singleton
    abstract fun provideTodoRepository(todoRepository: TodoRepositoryImpl): TodoRepository

    @Binds
    @Singleton
    abstract fun provideTodoInteractor(todoInteractor: TodoInteractorImpl): TodoInteractor
    // _TODO
}