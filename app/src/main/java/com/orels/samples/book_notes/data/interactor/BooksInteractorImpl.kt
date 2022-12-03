package com.orels.samples.book_notes.data.interactor

import com.orels.samples.book_notes.data.local.LocalDatabase
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.Books
import com.orels.samples.book_notes.domain.repository.BooksRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BooksInteractorImpl @Inject constructor(
    private val repository: BooksRepository,
    localDatabase: LocalDatabase,
) : BooksInteractor {

    private val db = localDatabase.bookDao()

    override fun getAll(): Single<List<Book>> = db.getAll()
        .subscribeOn(Schedulers.io())
        .flatMap { books: List<Book> ->
            if (books.isEmpty()) {
                repository.getAll().observeOn(Schedulers.io())
                    .doOnSuccess { db.insert(it).subscribe() }
                    .doOnError { Single.just(emptyList<Book>()) }
            } else {
                Single.just(books)
            }
        }

    override fun get(id: String): Maybe<Book> = db.get(id).flatMap { book: Book ->
        if (book.id.isEmpty()) {
            repository.get(id).observeOn(Schedulers.io()).doOnSuccess { db.insert(it).subscribe() }
        } else {
            Maybe.just(book)
        }
    }

    override fun insert(book: Book): Single<Result<Book>> = repository.insert(book)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .doOnSuccess { id ->
            book.id = id
            db.insert(book).observeOn(Schedulers.io()).subscribe()
        }.map { Result.success(book) }

    override fun insert(books: Books): Single<Result<Books>> = repository.insert(books)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .doOnSuccess { ids ->
            books.forEachIndexed { index, book ->
                book.id = ids[index]
            }
            db.insert(books).observeOn(Schedulers.io()).subscribe()
        }.map { Result.success(books) }


    override fun update(book: Book): Completable =
        repository.update(book).observeOn(Schedulers.io())
            .doOnComplete { db.update(book).subscribe() }

    override fun delete(book: Book): Completable =
        repository.delete(book).observeOn(Schedulers.io())
            .doOnComplete { db.delete(book).subscribe() }

    override fun searchBook(searchText: String): Observable<Result<List<Book>>> =
        repository.searchBook(searchText)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .map {
                Result.success(it.toBooks())
            }
            .onErrorReturn { Result.failure(it) }

}