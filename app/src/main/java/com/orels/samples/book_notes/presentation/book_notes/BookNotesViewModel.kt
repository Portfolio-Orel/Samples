package com.orels.samples.book_notes.presentation.book_notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.samples.book_notes.domain.interactor.BookNotesInteractor
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.zipWith
import javax.inject.Inject

@HiltViewModel
class BookNotesViewModel @Inject constructor(
    private val booksInteractor: BooksInteractor,
    private val bookNotesInteractor: BookNotesInteractor,
) : ViewModel() {
    var state by mutableStateOf(BookNotesState())
        private set

    init {
        getData()
    }

    fun initData() = getData()

    fun onBookNotesEvent(event: BookNoteEvent) {
        when (event) {
            is BookNoteEvent.AddBookNote -> addBookNotes(event.bookNote)
            is BookNoteEvent.RemoveBookNote -> removeBookNote(event.bookNote)
            is BookNoteEvent.UpdateBookNote -> updateBookNote(event.bookNote)
        }
    }

    fun onBookEvent(event: BookEvent) {
        when (event) {
            is BookEvent.AddBook -> addBook(event.book)
            is BookEvent.RemoveBook -> removeBook(event.book)
            is BookEvent.UpdateBook -> updateBook(event.book)
        }
    }

    private fun setNewState(state: BookNotesState) {
        this.state = state
    }

    private fun getData() {
        state = state.copy(isLoading = true)
        booksInteractor.getAll().zipWith(bookNotesInteractor.getAll())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ (books, bookNotes) ->
                val bookNotesItems = books.map { book ->
                    BookNotesItem(book = book)
                }
                val bookNotesItemsWithNotes = bookNotesItems.map { bookNotesItem ->
                    val bookNotesForBook = bookNotes.getOrNull()?.filter { bookNote ->
                        bookNote.bookId == bookNotesItem.book?.id
                    }
                    bookNotesItem.copy(bookNotes = bookNotesForBook ?: emptyList())
                }
                setNewState(state.copy(bookNoteItems = bookNotesItemsWithNotes, isLoading = false))
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
                    val newBookNotes = bookNoteItem.bookNotes.filter { it.id != bookNote.id }
                    bookNoteItem.copy(bookNotes = newBookNotes)
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

    private fun addBook(book: Book) =
        booksInteractor.insert(book).observeOn(AndroidSchedulers.mainThread()).subscribe({
            setNewState(state.copy(bookNoteItems = state.bookNoteItems + BookNotesItem(book = book)))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })

    private fun removeBook(book: Book) =
        booksInteractor.delete(book).observeOn(AndroidSchedulers.mainThread()).subscribe({
            setNewState(state.copy(bookNoteItems = state.bookNoteItems.filter { it.book?.id != book.id }))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })

    private fun updateBook(book: Book) =
        booksInteractor.update(book).observeOn(AndroidSchedulers.mainThread()).subscribe({
            setNewState(state.copy(bookNoteItems = state.bookNoteItems.map { bookNoteItem ->
                if (bookNoteItem.book?.id == book.id) {
                    bookNoteItem.copy(book = book)
                } else {
                    bookNoteItem
                }
            }))
        }, { error ->
            setNewState(state.copy(error = error.message ?: "", isLoading = false))
        })

}
