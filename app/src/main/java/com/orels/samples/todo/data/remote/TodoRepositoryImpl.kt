package com.orels.samples.todo.data.remote

import com.orels.samples.todo.domain.model.Task
import com.orels.samples.todo.domain.repository.TodoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(): TodoRepository {
    override fun insert(task: Task): Single<String> = Single.just(UUID.randomUUID().toString())

    override fun insertAll(tasks: List<Task>): Completable = Completable.complete()

    override fun update(task: Task): Completable = Completable.complete()

    override fun delete(task: Task): Completable = Completable.complete()

    override fun getAll(): Single<List<Task>> = Single.just(listOf())

    override fun get(id: String): Single<Task> = Single.just(Task())
}