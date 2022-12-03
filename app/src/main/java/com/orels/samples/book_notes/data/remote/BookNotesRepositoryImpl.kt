package com.orels.samples.book_notes.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.orels.samples.book_notes.data.model.fromMapBookNote
import com.orels.samples.book_notes.data.model.toDelete
import com.orels.samples.book_notes.data.model.toInsert
import com.orels.samples.book_notes.data.model.toUpdate
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.domain.model.BookNotes
import com.orels.samples.book_notes.domain.repository.BookNotesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BookNotesRepositoryImpl @Inject constructor() : BookNotesRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun insert(bookNote: BookNote): Single<String> = // Insert a booknote to user's booknotes collection
        Single.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("bookNotes")
                .add(bookNote.toInsert())
                .addOnSuccessListener { documentReference ->
                    emitter.onSuccess(documentReference.id)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }


    override fun insert(bookNotes: BookNotes): Single<List<String>> =
        Single.create { emitter ->
            val batch = db.batch()
            val bookNotesIds = mutableListOf<String>()
            for (bookNote in bookNotes) {
                val bookNoteRef = db.collection("user")
                    .document("orel_dev")
                    .collection("bookNotes")
                    .document()
                batch.set(bookNoteRef, bookNote.toInsert())
                bookNotesIds.add(bookNoteRef.id)
            }
            batch.commit()
                .addOnSuccessListener {
                    emitter.onSuccess(bookNotesIds)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun update(bookNote: BookNote): Completable =
        Completable.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("bookNotes")
                .document(bookNote.id)
                .update(bookNote.toUpdate())
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun delete(bookNote: BookNote): Completable =
        Completable.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("bookNotes")
                .document(bookNote.id)
                .update(bookNote.toDelete())
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun getAll(): Single<List<BookNote>> =
        Single.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("bookNotes")
                .whereEqualTo("isActive", true)
                .get()
                .addOnSuccessListener { result ->
                    val bookNotes = mutableListOf<BookNote>()
                    for (document in result) {
                        bookNotes.add(fromMapBookNote(document.data, document.id))
                    }
                    emitter.onSuccess(bookNotes)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun get(id: String): Single<BookNote> =
        Single.create { emitter ->
            db.collection("user")
                .document("orel_dev")
                .collection("bookNotes")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    val bookNote = document.data?.let {
                        fromMapBookNote(
                            map = it,
                            id = document.id
                        )
                    }
                    if (bookNote != null) {
                        emitter.onSuccess(bookNote)
                    } else {
                        emitter.onError(Exception("BookNote not found"))
                    }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }
}