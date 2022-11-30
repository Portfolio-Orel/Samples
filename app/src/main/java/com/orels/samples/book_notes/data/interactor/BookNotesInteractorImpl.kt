package com.orels.samples.book_notes.data.interactor

import com.orels.samples.book_notes.data.local.LocalDatabase
import com.orels.samples.book_notes.domain.interactor.BookNotesInteractor
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.repository.BookNotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BookNotesInteractorImpl @Inject constructor(
    private val todoRepository: BookNotesRepository, localDatabase: LocalDatabase,
) : BookNotesInteractor {

    private val db = localDatabase.taskDao()

    override fun insert(bookNote: BookNote): Single<Result<BookNote>> =
        todoRepository.insert(bookNote).subscribeOn(Schedulers.io()).flatMap { id ->
            bookNote.id = id
            db.insert(bookNote).toSingleDefault(Result.success(bookNote))
        }.onErrorReturn {
            Result.failure(it)
        }


    override fun update(bookNote: BookNote): Completable =
        todoRepository.update(bookNote).subscribeOn(Schedulers.io()).doOnComplete {
            db.update(bookNote)
                .subscribe()
        }

    override fun delete(bookNote: BookNote): Completable =
        todoRepository.delete(bookNote).subscribeOn(Schedulers.io()).doOnComplete {
            db.delete(bookNote.id)
                .subscribe()
        }


    override fun getAll(): Single<Result<List<BookNote>>> =
        db.getAll().subscribeOn(Schedulers.io()).flatMap { list ->
            if (list.isEmpty()) {
                todoRepository.getAll().subscribeOn(Schedulers.io()).flatMapCompletable { listFromRemote ->
                    db.insertAll(listFromRemote).subscribeOn(Schedulers.io())
                }.andThen(Single.just(Result.success(list)))
            } else {
                Single.just(Result.success(list))
            }
        }.onErrorReturn {
            Result.failure(it)
        }

    override fun get(id: String): Single<Result<BookNote>> =
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