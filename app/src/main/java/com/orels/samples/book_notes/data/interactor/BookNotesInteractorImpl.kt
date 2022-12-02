package com.orels.samples.book_notes.data.interactor

import com.orels.samples.book_notes.data.local.LocalDatabase
import com.orels.samples.book_notes.domain.interactor.BookNotesInteractor
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.model.BookNotes
import com.orels.samples.book_notes.domain.repository.BookNotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BookNotesInteractorImpl @Inject constructor(
    private val repository: BookNotesRepository, localDatabase: LocalDatabase,
) : BookNotesInteractor {

    private val db = localDatabase.taskDao()

    override fun insert(bookNote: BookNote): Single<Result<BookNote>> =
        repository.insert(bookNote).subscribeOn(Schedulers.io()).flatMap { id ->
            bookNote.id = id
            db.insert(bookNote).toSingleDefault(Result.success(bookNote))
        }.onErrorReturn {
            Result.failure(it)
        }

    override fun insert(bookNotes: BookNotes): Single<Result<List<BookNote>>> =
        repository.insert(bookNotes).subscribeOn(Schedulers.io()).flatMap { ids ->
            bookNotes.forEachIndexed { index, bookNote ->
                bookNote.id = ids[index]
            }
            db.insert(bookNotes).toSingleDefault(Result.success(bookNotes))
        }.onErrorReturn {
            Result.failure(it)
        }


    override fun update(bookNote: BookNote): Completable =
        repository.update(bookNote).subscribeOn(Schedulers.io()).doOnComplete {
            db.update(bookNote).observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe()
        }

    override fun delete(bookNote: BookNote): Completable =
        repository.delete(bookNote).subscribeOn(Schedulers.io()).doOnComplete {
            db.delete(bookNote.id).observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe()
        }


    override fun getAll(): Single<Result<List<BookNote>>> =
        db.getAll().subscribeOn(Schedulers.io()).flatMap { list ->
            if (list.isEmpty()) {
                repository.getAll().subscribeOn(Schedulers.io())
                    .flatMapCompletable { listFromRemote ->
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
                repository.get(id).subscribeOn(Schedulers.io()).doOnSuccess { t ->
                    db.insert(t).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe()
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