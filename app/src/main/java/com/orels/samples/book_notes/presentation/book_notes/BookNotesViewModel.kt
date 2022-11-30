package com.orels.samples.book_notes.presentation.book_notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.samples.book_notes.domain.interactor.BookNotesInteractor
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class BookNotesViewModel @Inject constructor(
    private val booksInteractor: BooksInteractor,
    private val bookNotesInteractor: BookNotesInteractor,
) : ViewModel() {
    var state by mutableStateOf(BookNoteSState())
        private set

    init {
        getData()
    }

    fun onEvent(event: BookNoteEvent) {
        when (event) {
            is BookNoteEvent.AddBookNote -> addBookNotes(event.bookNote)
            is BookNoteEvent.RemoveBookNote -> removeBookNote(event.bookNote)
            is BookNoteEvent.UpdateBookNote -> updateBookNote(event.bookNote)
            else -> {}
        }
    }

    private fun setNewState(state: BookNoteSState) {
        this.state = state
    }

    private fun getData() {
        state = state.copy(isLoading = true)
        booksInteractor.getAll().zipWith(bookNotesInteractor.getAll()) { books, bookNotes ->
            books.map { book ->
                BookNotesItem(book = book)
            }.map { bookNotesItem ->
                bookNotesItem.copy(bookNotes = bookNotes.getOrNull()
                    ?.filter { it.bookId == bookNotesItem.book?.id } ?: emptyList())
            }
        }.observeOn(AndroidSchedulers.mainThread()).subscribe({ bookNoteItems ->
            setNewState(state.copy(bookNoteItems = bookNoteItems, isLoading = false))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })
    }

    private fun addBookNotes(bookNote: BookNote) =
        bookNotesInteractor.insert(bookNote).observeOn(AndroidSchedulers.mainThread()).subscribe({
            setNewState(state.copy(bookNoteItems = state.bookNoteItems.map { bookNoteItem ->
                if (bookNoteItem.book?.id == bookNote.bookId) {
                    bookNoteItem.copy(bookNotes = bookNoteItem.bookNotes + bookNote)
                } else {
                    bookNoteItem
                }
            }))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })

    private fun removeBookNote(bookNote: BookNote) =
        bookNotesInteractor.delete(bookNote).observeOn(AndroidSchedulers.mainThread()).subscribe({
            setNewState(state.copy(bookNoteItems = state.bookNoteItems.map { bookNoteItem ->
                if (bookNoteItem.book?.id == bookNote.bookId) {
                    bookNoteItem.copy(bookNotes = bookNoteItem.bookNotes - bookNote)
                } else {
                    bookNoteItem
                }
            }))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })

    private fun updateBookNote(bookNote: BookNote) =
        bookNotesInteractor.update(bookNote).observeOn(AndroidSchedulers.mainThread()).subscribe({
            setNewState(state.copy(bookNoteItems = state.bookNoteItems.map { bookNoteItem ->
                if (bookNoteItem.book?.id == bookNote.bookId) {
                    bookNoteItem.copy(bookNotes = bookNoteItem.bookNotes.map { bookNoteItemToUpdate ->
                        if (bookNoteItemToUpdate.id == bookNote.id) {
                            bookNote
                        } else {
                            bookNoteItemToUpdate
                        }
                    })
                } else {
                    bookNoteItem
                }
            }))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })
}
