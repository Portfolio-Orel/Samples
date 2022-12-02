package com.orels.samples.book_notes.data.remote

import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.model.BookNotes
import com.orels.samples.book_notes.domain.repository.BookNotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class BookNotesRepositoryImpl @Inject constructor() : BookNotesRepository {
    override fun insert(bookNote: BookNote): Single<String> =
        Single.just(UUID.randomUUID().toString())

    override fun insert(bookNotes: BookNotes): Single<List<String>> = Single.just(
        List(bookNotes.size) { UUID.randomUUID().toString() }
    )

    override fun update(bookNote: BookNote): Completable = Completable.complete()

    override fun delete(bookNote: BookNote): Completable = Completable.complete()

    override fun getAll(): Single<List<BookNote>> = Single.just(listOf())

    override fun get(id: String): Single<BookNote> = Single.just(BookNote())
}