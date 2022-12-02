package com.orels.samples.book_notes.domain.interactor

import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.model.BookNotes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookNotesInteractor {

    /**
     * Inserts a list of new book notes into the remote database and with the ids received from the remote, insert into local
     * @param bookNote the book note to insert
     * @return the book note with the new id
     */
    fun insert(bookNote: BookNote): Single<Result<BookNote>>

    /**
     * Inserts a list of new book notes into the remote database and with the ids received from the remote, insert into local
     * @param bookNotes the book notes to insert
     * @return the book notes with the new ids
     * @return the book notes with the new ids
     */
    fun insert(bookNotes: BookNotes): Single<Result<List<BookNote>>>

    /**
     * Updates a book note in the remote database and then update in local
     * @param bookNote the book note to update
     */
    fun update(bookNote: BookNote): Completable

    /**
     * Deletes a book note from the remote database and then delete from local
     * @param bookNote the book note to delete
     */
    fun delete(bookNote: BookNote): Completable

    /**
     * Gets all book note from the local database and if not found, get from remote and save to local
     * @return a list of book notes
     */
    fun getAll(): Single<Result<List<BookNote>>>

    /**
     * Gets a book note from the local database and if not found, get from remote and save to local
     * @param id the id of the book note to get
     * @return a book note
     */
    fun get(id: String): Single<Result<BookNote>>

}