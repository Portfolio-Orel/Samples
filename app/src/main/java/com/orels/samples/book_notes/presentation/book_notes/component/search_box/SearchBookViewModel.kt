package com.orels.samples.book_notes.presentation.book_notes.component.search_box

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.samples.book_notes.domain.interactor.BooksInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(private val booksInteractor: BooksInteractor) :
    ViewModel(
    ) {
    var state: SearchBoxState by mutableStateOf(SearchBoxState())

    private fun setNewState(state: SearchBoxState) {
        this.state = state
    }

    fun onSearchBoxEvent(event: SearchBoxEvent) =
        when (event) {
            is SearchBoxEvent.StartSearch -> searchBook(event.text)
        }


    private fun searchBook(searchQuery: String) =
        setNewState(state.copy(isLoading = true))
            .also {
                booksInteractor.searchBook(searchQuery).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        setNewState(state.copy(searchResult = it.getOrNull() ?: emptyList()))
                    }, { error ->
                        setNewState(state.copy(error = error.message ?: ""))
                    }, {
                        setNewState(state.copy(isLoading = false))
                    })
            }

}