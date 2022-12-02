package com.orels.samples.app.di

import com.orels.rx_weather.data.remote.interactor.WeatherInteractorImpl
import com.orels.rx_weather.domain.interactor.WeatherInteractor
import com.orels.samples.book_notes.data.interactor.BookNotesInteractorImpl
import com.orels.samples.book_notes.data.interactor.BooksInteractorImpl
import com.orels.samples.book_notes.domain.repository.BookNotesRepository
import com.orels.samples.book_notes.domain.repository.BooksRepository
import com.orels.samples.book_notes.data.remote.BookNotesRepositoryImpl
import com.orels.samples.book_notes.data.remote.BooksRepositoryImpl
import com.orels.samples.book_notes.domain.interactor.BookNotesInteractor
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
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


    // BookNotes
    @Binds
    @Singleton
    abstract fun provideBookNotesRepository(todoRepository: BookNotesRepositoryImpl): BookNotesRepository

    @Binds
    @Singleton
    abstract fun provideBookNotesInteractor(todoInteractor: BookNotesInteractorImpl): BookNotesInteractor

    @Binds
    @Singleton
    abstract fun provideBooksRepository(booksRepository: BooksRepositoryImpl): BooksRepository

    @Binds
    @Singleton
    abstract fun provideBooksInteractor(booksInteractor: BooksInteractorImpl): BooksInteractor
    // BookNotes
}