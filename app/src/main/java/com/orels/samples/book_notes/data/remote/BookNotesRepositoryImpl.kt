package com.orels.samples.book_notes.data.remote

import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.repository.BookNotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class BookNotesRepositoryImpl @Inject constructor(): BookNotesRepository {
    override fun insert(bookNote: BookNote): Single<String> = Single.just(UUID.randomUUID().toString())

    override fun insertAll(bookNotes: List<BookNote>): Completable = Completable.complete()

    override fun update(bookNote: BookNote): Completable = Completable.complete()

    override fun delete(bookNote: BookNote): Completable = Completable.complete()

    override fun getAll(): Single<List<BookNote>> = Single.just(listOf())

    override fun get(id: String): Single<BookNote> = Single.just(BookNote())
}