package com.orels.samples.book_notes.presentation.book_notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.components.Loading
import com.orels.samples.R
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.component.AddBook
import com.orels.samples.book_notes.presentation.book_notes.component.AddNewBookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem
import com.orels.samples.ui.multi_fab.MiniFloatingAction
import com.orels.samples.ui.multi_fab.MultiFab

@Composable
fun BookNotesScreen(viewModel: BookNotesViewModel = hiltViewModel()) {
    val state = viewModel.state
    var shouldShowAddBookNote by remember { mutableStateOf(false) }
    var shouldShowAddBook by remember { mutableStateOf(false) }

    if (shouldShowAddBookNote) {
        AddNewBookNote(
            onAddBookNote = {
                viewModel.onBookNotesEvent(BookNoteEvent.AddBookNote(
                    it
                ))
            },
            onDismiss = { shouldShowAddBookNote = false },
            books = state.bookNoteItems.map { it.book ?: Book() }
        )
    }
    if (shouldShowAddBook) {
        AddBook(onAddBook = { viewModel.onBookEvent(BookEvent.AddBook(it)) },
            onDismiss = { shouldShowAddBook = false })
    }
    if (state.isLoading) {
        Loading(size = 16.dp, width = 2.dp)
    } else {
        Box(contentAlignment = Alignment.TopCenter) {
            Column {
                BookNotesList(bookNotes = state.bookNoteItems, onDelete = { task ->
                    viewModel.onBookNotesEvent(BookNoteEvent.RemoveBookNote(task))
                }, onUpdate = { task ->
                    viewModel.onBookNotesEvent(BookNoteEvent.UpdateBookNote(task))
                })
            }
            MultiFab(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                miniFloatingActionButtons = listOf(MiniFloatingAction(
                    icon = R.drawable.ic_round_note,
                    action = { shouldShowAddBookNote = true }
                ), MiniFloatingAction(
                    icon = R.drawable.ic_book,
                    action = { shouldShowAddBook = true }
                )),
                iconCollapsed = R.drawable.ic_round_add,
                iconExpanded = R.drawable.ic_round_close)
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
            Text(text = bookNotesItem.book?.title ?: stringResource(id = R.string.book),
                style = MaterialTheme.typography.titleLarge)
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
                    text = stringResource(R.string.x_caps),
                    modifier = Modifier
                        .clickable {
                            onDelete(it)
                        }
                        .padding(horizontal = 8.dp),
                )
                Text(text = it.title)
            }
        }
    }
}
