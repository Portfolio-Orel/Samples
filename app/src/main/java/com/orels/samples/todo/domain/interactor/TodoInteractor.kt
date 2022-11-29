package com.orels.samples.todo.domain.interactor

import com.orels.samples.todo.domain.model.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface TodoInteractor {
    /**
     * Inserts a new task into the database
     * @param task the task to insert
     * @return the task with the new id
     */
    fun insert(task: Task): Single<Result<Task>>

    /**
     * Updates a task in the database
     * @param task the task to update
     * @return the number of rows affected
     */
    fun update(task: Task): Completable

    /**
     * Deletes a task from the database
     * @param task the task to delete
     * @return the number of rows affected
     */
    fun delete(task: Task): Completable

    /**
     * Gets all tasks from the database
     * @return a flowable list of tasks
     */
    fun getAll(): Flowable<Result<List<Task>>>

    /**
     * Gets a task from the database
     * @param id the id of the task to get
     * @return a flowable task
     */
    fun get(id: String): Single<Result<Task>>

}