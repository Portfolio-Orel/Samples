package com.orels.samples.book_notes.domain.repository

import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.model.BookNotes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookNotesRepository {
    /**
     * Inserts a new book note into the database
     * @param bookNote the book note to insert
     * @return the id of the inserted book notes
     */
    fun insert(bookNote: BookNote): Single<String>

    /**
     * Inserts a list of new book notes into the database
     * @param bookNotes the list of book notes to insert
     * @return the ids of the inserted book notes
     */
    fun insert(bookNotes: BookNotes): Single<List<String>>

    /**
     * Updates a book note in the database
     * @param bookNote the book note to update
     * @return the number of rows affected
     */
    fun update(bookNote: BookNote): Completable

    /**
     * Deletes a task from the database
     * @param bookNote the task to delete
     * @return the number of rows affected
     */
    fun delete(bookNote: BookNote): Completable

    /**
     * Gets all book notes from the database
     * @return a flowable list of book notes
     */
    fun getAll(): Single<List<BookNote>>

    /**
     * Gets a book note from the database
     * @param id the id of the book note to get
     * @return a flowable book note
     */
    fun get(id: String): Single<BookNote>
}