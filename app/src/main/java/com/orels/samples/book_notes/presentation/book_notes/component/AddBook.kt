package com.orels.samples.book_notes.presentation.book_notes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.orels.components.Input
import com.orels.samples.R
import com.orels.samples.book_notes.domain.model.Book

@Composable
fun AddBook(onAddBook: (Book) -> Unit, onDismiss: () -> Unit) {

    var bookTitle by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Input(
                title = stringResource(R.string.book_title),
                placeholder = stringResource(R.string.the_lord_of_the_rings),
                onTextChange = { bookTitle = it }
            )
            Button(
                onClick = {
                    if (bookTitle.isNotBlank()) {
                        onAddBook(Book(title = bookTitle))
                        onDismiss()
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text(
                    text = stringResource(R.string.add),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}