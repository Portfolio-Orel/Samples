package com.orels.samples.book_notes.presentation.book_notes.model

import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.BookNote

data class BookNotesItem(
    val book: Book? = null,
    val bookNotes: List<BookNote> = emptyList(),
)