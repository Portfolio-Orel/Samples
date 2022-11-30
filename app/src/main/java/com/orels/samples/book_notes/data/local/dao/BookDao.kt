package com.orels.samples.book_notes.data.local.dao

import androidx.room.*
import com.orels.samples.book_notes.domain.model.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface BookDao {

    @Query("SELECT * FROM Book WHERE isActive = 1")
    fun getAll(): Single<List<Book>>

    @Query("SELECT * FROM Book WHERE id = :id AND isActive = 1")
    fun get(id: String): Maybe<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: List<Book>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: Book): Completable

    @Delete
    fun delete(book: Book): Completable

    @Update
    fun update(book: Book): Completable
}