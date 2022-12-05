package com.orels.samples.book_notes.presentation.add_book

import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.Books

data class AddBookState(val isLoading: Boolean = false, val savedBooks: Books = emptyList(), val error: String = "")

sealed class AddBookEvent {
    data class AddBook(val book: Book) : AddBookEvent()
    data class RemoveBook(val book: Book) : AddBookEvent()
}