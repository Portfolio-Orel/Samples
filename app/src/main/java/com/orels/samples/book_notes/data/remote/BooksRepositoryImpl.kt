package com.orels.samples.book_notes.data.remote

import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.repository.BooksRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor() : BooksRepository {
    override fun getAll(): Single<List<Book>> = Single.just(listOf())

    override fun get(id: String): Maybe<Book> = Maybe.empty()

    override fun insert(book: Book): Single<String> = Single.just("First book")

    override fun update(book: Book): Completable = Completable.complete()

    override fun delete(book: Book): Completable = Completable.complete()
}