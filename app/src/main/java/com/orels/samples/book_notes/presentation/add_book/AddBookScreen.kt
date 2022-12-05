package com.orels.samples.book_notes.presentation.add_book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.presentation.component.BookRowComponent
import com.orels.samples.book_notes.presentation.component.search_box.SearchBookComponent

@Composable
fun AddBookScreen(viewModel: AddBookViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.state
    var searchResult by remember { mutableStateOf(emptyList<Book>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        SearchBookComponent(onSearchResult = { searchResult = it })

        LazyColumn {
            items(searchResult.size) { index ->
                BookRowComponent(
                    book = searchResult[index],
                    onAddBook = {
                        viewModel.onBookEvent(AddBookEvent.AddBook(it))
                    },
                    onRemoveBook = {
                        viewModel.onBookEvent(AddBookEvent.RemoveBook(it))
                    },
                    isSaved = state.savedBooks.contains(searchResult[index]),
                )
            }
        }

    }
}