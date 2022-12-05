package com.orels.samples.book_notes.presentation.component.search_box

import com.orels.samples.book_notes.domain.model.Books

data class SearchBoxState(
    val isLoading: Boolean = false,
    val searchResult: Books = emptyList(),
    val error: String = "",
)

sealed class SearchBoxEvent {
    data class StartSearch(val text: String) : SearchBoxEvent()
}