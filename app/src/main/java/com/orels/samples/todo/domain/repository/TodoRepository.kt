package com.orels.samples.todo.domain.repository

import com.orels.samples.todo.domain.model.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface TodoRepository {
    /**
     * Inserts a new task into the database
     * @param task the task to insert
     * @return the id of the inserted task
     */
    fun insert(task: Task): Single<String>

    /**
     * Inserts a list of new tasks into the database
     * @param tasks the list of tasks to insert
     * @return the number of rows affected
     */
    fun insertAll(tasks: List<Task>): Completable

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
    fun getAll(): Single<List<Task>>

    /**
     * Gets a task from the database
     * @param id the id of the task to get
     * @return a flowable task
     */
    fun get(id: String): Single<Task>
}