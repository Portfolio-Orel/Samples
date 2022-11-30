package com.orels.samples.book_notes.presentation.book_notes

import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem

data class BookNotesState(
    val isLoading: Boolean = false,
    val bookNoteItems: List<BookNotesItem> = emptyList(),
    val error: String = "",
)

sealed class BookNoteEvent {
    object GetTodos : BookNoteEvent()
    data class AddBookNote(val bookNote: BookNote) : BookNoteEvent()
    data class RemoveBookNote(val bookNote: BookNote) : BookNoteEvent()
    data class UpdateBookNote(val bookNote: BookNote) : BookNoteEvent()
}