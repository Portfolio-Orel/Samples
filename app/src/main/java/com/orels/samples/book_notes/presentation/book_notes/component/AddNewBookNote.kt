package com.orels.samples.book_notes.presentation.book_notes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.orels.components.Dropdown
import com.orels.components.Input
import com.orels.samples.R
import com.orels.samples.book_notes.domain.model.Books

@Composable
fun AddNewBookNote(onAddBook: (String) -> Unit, onDismiss: () -> Unit, books: Books) {
    var selectedBook by remember { mutableStateOf(books.firstOrNull()) }
    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )) {
        Column(modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Dropdown(
                modifier = Modifier.padding(16.dp),
                items = books,
                onSelected = { selectedBook = it },
                selectedIndex = books.indexOf(selectedBook),
                defaultTitle = R.string.select_a_book,
                secondaryIcon = Icons.Rounded.Edit,
                color = Color.Red,
            )
            Input(
                modifier = Modifier.padding(16.dp),
                placeholder = "Enter a title for the note",
            )
            Input(modifier = Modifier.padding(16.dp),
                placeholder = "Enter the page of the note")
            Input(
                modifier = Modifier.padding(16.dp),
                placeholder = "Enter a note",
                minLines = 5,
                maxLines = 5,
            )

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    onAddBook(selectedBook?.title ?: "")
                    onDismiss()
                },
            ) {
                Text(
                    text = stringResource(R.string.add_note),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }

}
