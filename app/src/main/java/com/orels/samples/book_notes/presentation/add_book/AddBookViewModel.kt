package com.orels.samples.book_notes.presentation.add_book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
import com.orels.samples.book_notes.domain.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(private val booksInteractor: BooksInteractor) :
    ViewModel() {
    var state by mutableStateOf(AddBookState())
        private set

    private fun setNewState(state: AddBookState) {
        this.state = state
    }

    fun onBookEvent(event: AddBookEvent) =
        when (event) {
            is AddBookEvent.AddBook -> addBook(event.book)
            is AddBookEvent.RemoveBook -> removeBook(event.book)
        }

    private fun addBook(book: Book) =
        setNewState(state.copy(isLoading = true))
            .also {
                booksInteractor.insert(book)
                    .subscribe({
                        setNewState(state.copy(isLoading = false,
                            savedBooks = state.savedBooks + book))
                    }, { error ->
                        setNewState(state.copy(error = error.message ?: ""))
                    })
            }

    private fun removeBook(book: Book) = setNewState(state.copy(isLoading = true))
        .also {
            booksInteractor.delete(book)
                .subscribe({
                    setNewState(state.copy(isLoading = false, savedBooks = state.savedBooks - book))
                }, { error ->
                    setNewState(state.copy(error = error.message ?: ""))
                })
        }

}