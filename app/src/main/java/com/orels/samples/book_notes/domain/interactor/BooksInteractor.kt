package com.orels.samples.book_notes.domain.interactor

import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.Books
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface BooksInteractor {
    /**
     * Get all books from local first, if not found, get from remote and save to local
     * @return list of books
     */
    fun getAll(): Single<List<Book>>

    /**
     * Get a book from local first, if not found, get from remote and save to local
     * @param id the id of the book to get
     * @return a book
     */
    fun get(id: String): Maybe<Book>

    /**
     * Inserts a new book into the remote database, then with the id received from the remote, insert into local
     * @param book the book to insert
     * @return the book with the id of the inserted book
     */
    fun insert(book: Book): Single<Result<Book>>

    /**
     * Inserts a new book into the remote database, then with the ids received from the remote, insert into local
     * @param books the books to insert
     * @return the the books with  of the inserted books
     */
    fun insert(books: Books): Single<Result<List<Book>>>

    /**
     * Updates a book in the remote database, then update in local
     * @param book the book to update
     */
    fun update(book: Book): Completable

    /**
     * Deletes a book from the remote database, then delete from local
     * @param book the book to delete
     */
    fun delete(book: Book): Completable

    /**
     * Search a book with the searchText using Google Books API
     * @param searchText the text to search
     * @return a list of BookSearchResult
     * @author Orel Zilberman
     */
    fun searchBook(searchText: String): Observable<Result<List<Book>>>
}