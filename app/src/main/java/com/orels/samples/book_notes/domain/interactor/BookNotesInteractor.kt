package com.orels.samples.book_notes.domain.interactor

import com.orels.samples.book_notes.domain.model.BookNote
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookNotesInteractor {

    /**
     * Inserts a list of new book notes into the database
     * @param bookNote the book note to insert
     * @return the book note with the new id
     */
    fun insert(bookNote: BookNote): Single<Result<BookNote>>


    /**
     * Updates a book note in the database
     * @param bookNote the book note to update
     * @return the number of rows affected
     */
    fun update(bookNote: BookNote): Completable

    /**
     * Deletes a book note from the database
     * @param bookNote the book note to delete
     * @return the number of rows affected
     */
    fun delete(bookNote: BookNote): Completable

    /**
     * Gets all book note from the database
     * @return a flowable list of book notes
     */
    fun getAll(): Single<Result<List<BookNote>>>

    /**
     * Gets a book note from the database
     * @param id the id of the book note to get
     * @return a flowable book note
     */
    fun get(id: String): Single<Result<BookNote>>

}