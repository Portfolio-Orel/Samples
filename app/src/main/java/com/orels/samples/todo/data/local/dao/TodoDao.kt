package com.orels.samples.todo.data.local.dao

import androidx.room.*
import com.orels.samples.todo.domain.model.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tasks: List<Task>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task): Completable

    @Query("UPDATE Task SET isActive = 0 WHERE id = :id")
    fun delete(id: String): Completable

    @Query("UPDATE Task SET isActive = 1 WHERE id = :id")
    fun restore(id: String): Completable

    @Query("""
        SELECT * FROM Task
        WHERE isActive = 1
    """)
    fun getAll(): Flowable<List<Task>>

    @Query("""
        SELECT * FROM Task
        WHERE id = :id AND isActive = 1
    """)
    fun get(id: String): Single<Task>

}