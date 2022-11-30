package com.orels.samples.book_notes.domain.repository

import com.orels.samples.book_notes.domain.model.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface BooksRepository {
    /**
     * Get all books from remote
     * @return list of books
     */
    fun getAll(): Single<List<Book>>

    /**
     * Get a book from remote
     * @param id the id of the book to get
     * @return a book
     */
    fun get(id: String): Maybe<Book>

    /**
     * Inserts a new book into the remote database
     * @param book the book to insert
     * @return the id of the inserted book
     */
    fun insert(book: Book): Single<String>

    /**
     * Updates a book in the remote database
     * @param book the book to update
     */
    fun update(book: Book): Completable

    /**
     * Deletes a book from the remote database
     * @param book the book to delete
     */
    fun delete(book: Book): Completable
}