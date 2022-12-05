package com.orels.samples.book_notes.presentation.book_notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orels.components.Loading
import com.orels.components.OnLifecycleEvent
import com.orels.samples.R
import com.orels.samples.book_notes.common.Screen
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.BookNote
import com.orels.samples.book_notes.presentation.book_notes.component.AddBook
import com.orels.samples.book_notes.presentation.book_notes.component.AddNewBookNote
import com.orels.samples.book_notes.presentation.book_notes.model.BookNotesItem
import com.orels.samples.book_notes.presentation.component.BookRowComponent
import com.orels.samples.ui.multi_fab.MiniFloatingAction
import com.orels.samples.ui.multi_fab.MultiFab

@Composable
fun BookNotesScreen(viewModel: BookNotesViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.state
    var shouldShowAddBookNote by remember { mutableStateOf(false) }
    var shouldShowAddBook by remember { mutableStateOf(false) }

    OnLifecycleEvent(onResume = viewModel::initData)

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
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            Box(contentAlignment = Alignment.TopCenter) {
                BookNotesList(bookNotes = state.bookNoteItems, onDeleteBookNote = { task ->
                    viewModel.onBookNotesEvent(BookNoteEvent.RemoveBookNote(task))
                }, onUpdateBookNote = { task ->
                    viewModel.onBookNotesEvent(BookNoteEvent.UpdateBookNote(task))
                },
                    onRemoveBook = { viewModel.onBookEvent(BookEvent.RemoveBook(it)) })
            }
            Spacer(Modifier.weight(1f))
            Box(contentAlignment = Alignment.Center) {
                MultiFab(
                    modifier = Modifier
                        .padding(bottom = 40.dp),
                    miniFloatingActionButtons = listOf(MiniFloatingAction(
                        icon = R.drawable.ic_round_note,
                        action = { shouldShowAddBookNote = true }
                    ), MiniFloatingAction(
                        icon = R.drawable.ic_book,
                        action = { navController.navigate(Screen.AddBook.route) }
                    )),
                    iconCollapsed = R.drawable.ic_round_add,
                    iconExpanded = R.drawable.ic_round_close)
            }
        }
    }
}

@Composable
fun BookNotesList(
    bookNotes: List<BookNotesItem>,
    onDeleteBookNote: (BookNote) -> Unit,
    onUpdateBookNote: (BookNote) -> Unit,
    onRemoveBook: (Book) -> Unit,
) {
    Column {
        bookNotes.forEach { bookNotesItem ->
            BookRowComponent(book = bookNotesItem.book ?: Book(),
                onAddBook = {},
                onRemoveBook = onRemoveBook,
                isSaved = true)
            BookNoteItemComponent(bookNotesItem = bookNotesItem,
                onDelete = onDeleteBookNote,
                onUpdate = onUpdateBookNote)
        }
    }
}

@Composable
fun BookNoteItemComponent(
    bookNotesItem: BookNotesItem,
    onDelete: (BookNote) -> Unit,
    onUpdate: (BookNote) -> Unit,
) {
    Column {
        bookNotesItem.bookNotes.forEach {
            Row {
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
