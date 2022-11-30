package com.orels.samples.book_notes.presentation.book_notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.components.Loading
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem

@Composable
fun TodoScreen(viewModel: BookNotesViewModel = hiltViewModel()) {
    val state = viewModel.state

    if (state.isLoading) {
        Loading(size = 16.dp, width = 2.dp)
    } else {
        Column {

            BookNotesList(bookNotes = state.bookNoteItems, onDelete = { task ->
                viewModel.onEvent(BookNoteEvent.RemoveBookNote(task))
            }, onUpdate = { task ->
                viewModel.onEvent(BookNoteEvent.UpdateBookNote(task))
            })
        }
    }
}

@Composable
fun BookNotesList(
    bookNotes: List<BookNotesItem>,
    onDelete: (BookNote) -> Unit,
    onUpdate: (BookNote) -> Unit,
) {
    Column() {
        bookNotes.forEach { bookNotesItem ->
            Text(
                text = bookNotesItem.book?.title ?: "Book",
                style = MaterialTheme.typography.titleLarge
            )
            BookNoteItemComponent(bookNotesItem = bookNotesItem,
                onDelete = onDelete,
                onUpdate = onUpdate)
        }
    }
}

@Composable
fun BookNoteItemComponent(
    bookNotesItem: BookNotesItem,
    onDelete: (BookNote) -> Unit,
    onUpdate: (BookNote) -> Unit,
) {
    Column() {
        bookNotesItem.bookNotes.forEach {
            Row() {
                Text(
                    text = "X",
                    modifier = Modifier
                        .clickable {
                            onDelete(it)
                        }
                        .padding(horizontal = 8.dp),
                )
                Text(text = it.note ?: "Hello")
            }
        }
    }
}
