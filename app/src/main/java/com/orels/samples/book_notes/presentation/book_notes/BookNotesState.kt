package com.orels.samples.book_notes.presentation.book_notes

import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem

data class BookNoteSState(
    val isLoading: Boolean = false,
    val bookNoteItems: List<BookNotesItem> = listOf(BookNotesItem(book = Book(id = "First book",
        title = "This is the first book",
        isActive = true))),
    val error: String = "",
)

sealed class BookNoteEvent {
    object GetTodos : BookNoteEvent()
    data class AddBookNote(val bookNote: BookNote) : BookNoteEvent()
    data class RemoveBookNote(val bookNote: BookNote) : BookNoteEvent()
    data class UpdateBookNote(val bookNote: BookNote) : BookNoteEvent()
}