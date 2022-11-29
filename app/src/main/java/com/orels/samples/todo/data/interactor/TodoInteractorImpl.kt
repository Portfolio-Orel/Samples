package com.orels.samples.todo.data.interactor

import com.orels.samples.todo.data.local.LocalDatabase
import com.orels.samples.todo.domain.interactor.TodoInteractor
import com.orels.samples.todo.domain.model.Task
import com.orels.samples.todo.domain.repository.TodoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TodoInteractorImpl @Inject constructor(
    private val todoRepository: TodoRepository, localDatabase: LocalDatabase,
) : TodoInteractor {

    private val db = localDatabase.todoDao()

    override fun insert(task: Task): Single<Result<Task>> =
        todoRepository.insert(task).subscribeOn(Schedulers.io()).flatMap { id ->
            task.id = id
            db.insert(task).subscribeOn(Schedulers.io())
                .toSingleDefault(Result.success(task))
        }.onErrorReturn {
            Result.failure(it)
        }


    override fun update(task: Task): Completable =
        todoRepository.update(task).subscribeOn(Schedulers.io()).doOnComplete { db.update(task) }

    override fun delete(task: Task): Completable =
        todoRepository.delete(task).subscribeOn(Schedulers.io()).doOnComplete { db.delete(task.id) }


    override fun getAll(): Flowable<Result<List<Task>>> =
        db.getAll().subscribeOn(Schedulers.io()).flatMap { list ->
            if (list.isEmpty()) {
                todoRepository.getAll().subscribeOn(Schedulers.io()).flatMapCompletable { list ->
                    db.insertAll(list).subscribeOn(Schedulers.io())
                }.andThen(Flowable.just(Result.success(list)))
            } else {
                Flowable.just(Result.success(list))
            }
        }.onErrorReturn {
            Result.failure(it)
        }

    override fun get(id: String): Single<Result<Task>> =
        db.get(id).subscribeOn(Schedulers.io()).flatMap { task ->
            if (task.isNullOrEmpty()) {
                todoRepository.get(id).subscribeOn(Schedulers.io()).doOnSuccess { t ->
                    db.insert(t).subscribeOn(Schedulers.io()).subscribe()
                }
            } else {
                Single.just(task)
            }
        }.map {
            Result.success(it)
        }.onErrorReturn {
            Result.failure(it)
        }


}