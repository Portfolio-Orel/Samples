package com.orels.samples.book_notes.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.orels.samples.book_notes.data.model.*
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.Books
import com.orels.samples.book_notes.domain.repository.BooksRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val api: BooksAPI,
) : BooksRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getAll(): Single<Books> =
        Single.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("books")
                .whereEqualTo("isActive", true)
                .get()
                .addOnSuccessListener { result ->
                    val books = mutableListOf<Book>()
                    for (document in result) {
                        books.add(fromMapBook(document.data, document.id))
                    }
                    emitter.onSuccess(books)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun get(id: String): Maybe<Book> =
        Maybe.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("books")
                .document(id)
                .get()
                .addOnSuccessListener { result ->
                    val book = result.data?.let {
                        fromMapBook(it, result.id)
                    }
                    if (book != null) {
                        emitter.onSuccess(book)
                    } else {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun insert(book: Book): Single<String> =
        Single.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("books")
                .add(book.toInsert())
                .addOnSuccessListener { documentReference ->
                    emitter.onSuccess(documentReference.id)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun insert(books: Books): Single<List<String>> =
        Single.create { emitter ->
            val ids = mutableListOf<String>()
            for (book in books) {
                db.collection("user")
                    .document("orel_dev")
                    .collection("books")
                    .add(book.toInsert())
                    .addOnSuccessListener { documentReference ->
                        ids.add(documentReference.id)
                        if (ids.size == books.size) {
                            emitter.onSuccess(ids)
                        }
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
            }
        }

    override fun update(book: Book): Completable =
        Completable.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("books")
                .document(book.id)
                .update(book.toUpdate())
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun delete(book: Book): Completable =
        Completable.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("books")
                .document(book.id)
                .update(book.toDelete())
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun searchBook(searchText: String): Single<BookSearchResult> =
        api.searchBook(searchText)

}