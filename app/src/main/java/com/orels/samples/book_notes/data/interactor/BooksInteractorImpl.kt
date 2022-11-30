package com.orels.samples.book_notes.data.interactor

import com.orels.samples.book_notes.data.local.LocalDatabase
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.repository.BooksRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BooksInteractorImpl @Inject constructor(
    private val repository: BooksRepository,
    localDatabase: LocalDatabase,
) : BooksInteractor {

    private val db = localDatabase.bookDao()

    override fun getAll(): Single<List<Book>> = db.getAll()
        .flatMap { books: List<Book> ->
            if (books.isEmpty()) {
                repository.getAll()
                    .doOnSuccess { db.insert(it).subscribe() }
            } else {
                Single.just(books)
            }
        }

    override fun get(id: String): Maybe<Book> = db.get(id)
        .flatMap { book: Book ->
            if (book.id.isEmpty()) {
                repository.get(id)
                    .doOnSuccess { db.insert(it).subscribe() }
            } else {
                Maybe.just(book)
            }
        }

    override fun insert(book: Book): Single<String> = repository.insert(book)
        .doOnSuccess { db.insert(book).subscribe() }

    override fun update(book: Book): Completable = repository.update(book)
        .doOnComplete { db.update(book).subscribe() }

    override fun delete(book: Book): Completable = repository.delete(book)
        .doOnComplete { db.delete(book).subscribe() }
}