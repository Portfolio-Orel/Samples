package com.orels.samples.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.orels.rx_weather.data.remote.API
import com.orels.samples.app.annotation.BaseUrl
import com.orels.samples.book_notes.data.local.LocalDatabase
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

    // RX_WEATHER
    @Provides
    @BaseUrl
    fun provideBaseUrl(
    ): String = "https://api.open-meteo.com/v1/forecast/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesRxAdapter(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    @Provides
    fun providesAPI(
        rxAdapter: RxJava3CallAdapterFactory,
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrl url: String,
    ): API =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .baseUrl(url)
            .build()
            .create(API::class.java)
    // RX_WEATHER

    // BookNotes
    @Provides
    @Singleton
    fun provideLocalDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabase = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        "book_notes_db"
    )
        .fallbackToDestructiveMigration()
        .build()

    // BookNotes
}