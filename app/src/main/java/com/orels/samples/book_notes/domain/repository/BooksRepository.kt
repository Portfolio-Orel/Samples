package com.orels.samples.book_notes.domain.repository

import com.orels.samples.book_notes.data.model.BookSearchResult
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.Books
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
     * Inserts a new book into the remote database
     * @param books the books to insert
     * @return the ids of the inserted books
     */
    fun insert(books: Books): Single<List<String>>

    /**
     * Updates a book in the remote database
     * @param book the book to update
     */
    fun update(book: Book): Completable

    /**
     * Deletes a book from the remote database
     * @param book the book to delete\
     * @author Orel Zilberman
     */
    fun delete(book: Book): Completable

    /**
     * Search a book with the searchText using Google Books API
     * @param searchText the text to search
     * @return a list of BookSearchResult
     * @author Orel Zilberman
     */
    fun searchBook(searchText: String): Single<BookSearchResult>
}