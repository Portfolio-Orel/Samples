package com.orels.samples.todo.data.local.dao

import androidx.room.*
import com.orels.samples.todo.domain.model.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(todo: Todo): Completable

    @Delete
    fun delete(todo: Todo): Completable

    @Query(
        """
        SELECT * FROM Todo
    """
    )
    fun getTodos(): Flowable<Todo>

    @Query(
        """
        SELECT * FROM Todo
        WHERE id = :id
    """
    )
    fun getTodoById(id: String): Single<Todo>

}