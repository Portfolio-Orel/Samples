package com.orels.samples.book_notes.domain.repository

import com.orels.samples.book_notes.domain.model.BookNote
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookNotesRepository {
    /**
     * Inserts a new task into the database
     * @param bookNote the task to insert
     * @return the id of the inserted task
     */
    fun insert(bookNote: BookNote): Single<String>

    /**
     * Inserts a list of new tasks into the database
     * @param bookNotes the list of tasks to insert
     * @return the number of rows affected
     */
    fun insertAll(bookNotes: List<BookNote>): Completable

    /**
     * Updates a task in the database
     * @param bookNote the task to update
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
     * Gets all tasks from the database
     * @return a flowable list of tasks
     */
    fun getAll(): Single<List<BookNote>>

    /**
     * Gets a task from the database
     * @param id the id of the task to get
     * @return a flowable task
     */
    fun get(id: String): Single<BookNote>
}