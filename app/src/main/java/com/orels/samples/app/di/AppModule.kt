package com.orels.samples.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.orels.rx_weather.data.remote.WeatherAPI
import com.orels.samples.app.annotation.BaseUrlBooks
import com.orels.samples.app.annotation.BaseUrlWeather
import com.orels.samples.book_notes.data.interceptor.LogInterceptor
import com.orels.samples.book_notes.data.local.LocalDatabase
import com.orels.samples.book_notes.data.remote.BooksAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // SHARED
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(LogInterceptor())
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesRxAdapter(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()
    // SHARED

    // RX_WEATHER
    @Provides
    @BaseUrlWeather
    fun provideWeatherBaseUrl(
    ): String = "https://www.api.open-meteo.com/v1/forecast/"

    @Provides
    fun providesWeatherAPI(
        rxAdapter: RxJava3CallAdapterFactory,
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrlWeather url: String,
    ): WeatherAPI =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .baseUrl(url)
            .build()
            .create(WeatherAPI::class.java)
    // RX_WEATHER

    // BookNotes
    @Provides
    @Singleton
    fun provideBooksLocalDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabase = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        "book_notes_db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @BaseUrlBooks
    fun provideBooksBaseUrl(
    ): String = "https://www.googleapis.com/"

    @Provides
    fun providesBooksAPI(
        rxAdapter: RxJava3CallAdapterFactory,
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrlBooks url: String,
    ): BooksAPI =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .baseUrl(url)
            .build()
            .create(BooksAPI::class.java)

    // BookNotes
}