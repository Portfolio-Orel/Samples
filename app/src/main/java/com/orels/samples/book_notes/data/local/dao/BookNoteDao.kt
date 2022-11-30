package com.orels.samples.book_notes.data.local.dao

import androidx.room.*
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.model.BookNotes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


@Dao
interface BookNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bookNotes: List<BookNote>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookNote: BookNote): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookNote: BookNotes): Completable

    @Update
    fun update(bookNote: BookNote): Completable

    @Query("UPDATE BookNote SET isActive = 0 WHERE id = :id")
    fun delete(id: String): Completable

    @Query("UPDATE BookNote SET isActive = 1 WHERE id = :id")
    fun restore(id: String): Completable

    @Query("""
        SELECT * FROM BookNote
        WHERE isActive = 1
    """)
    fun getAll(): Single<List<BookNote>>

    @Query("""
        SELECT * FROM BookNote
        WHERE id = :id AND isActive = 1
    """)
    fun get(id: String): Single<BookNote>

}