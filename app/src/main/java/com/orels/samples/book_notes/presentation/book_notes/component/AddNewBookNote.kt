package com.orels.samples.book_notes.presentation.book_notes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.orels.components.*
import com.orels.extension.multiplyByGoldenRatio
import com.orels.samples.R
import com.orels.samples.book_notes.domain.model.Books

private enum class BookType {
    Book, AudioBook;

    companion object {
        fun fromName(name: String?): BookType? {
            values().forEach {
                if (it.name == name) return it
            }
            return null
        }
    }
}

@Composable
fun AddNewBookNote(onAddBook: (String) -> Unit, onDismiss: () -> Unit, books: Books) {
    var selectedBook by remember { mutableStateOf(books.firstOrNull()) }
    var bookType by rememberSaveable { mutableStateOf(BookType.Book) }
    var bookNote by remember { mutableStateOf("") }
    var noteTitle by remember { mutableStateOf("") }
    var bookPage by remember { mutableStateOf(0) }
    var audioBookHour by remember { mutableStateOf(0) }
    var audioBookMinute by remember { mutableStateOf(0) }
    var audioBookSecond by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {
            Tabs(
                height = 30.dp,
                width = 50.dp.multiplyByGoldenRatio(),
                paddingBetweenItems = 16.dp,
                items = listOf(TabItem(title = R.string.book,
                    icon = R.drawable.ic_book,
                    identifier = BookType.Book.name),
                    TabItem(title = R.string.audio_book,
                        icon = R.drawable.ic_audiobook,
                        identifier = BookType.AudioBook.name)),
                onClick = {
                    bookType = BookType.fromName(it.identifier) ?: BookType.Book
                },
            )

            Dropdown(
                items = books,
                onSelected = { selectedBook = it },
                selectedIndex = books.indexOf(selectedBook),
                defaultTitle = R.string.select_a_book,
                secondaryIcon = Icons.Rounded.Edit,
                color = Color.Red,
            )
            Input(
                placeholder = stringResource(R.string.enter_a_title_for_the_note),
                onTextChange = { noteTitle = it },
            )
            if (bookType == BookType.Book) {
                LocationInBook(bookPage = bookPage, onBookPageChange = { bookPage = it })
            } else {
                LocationInAudioBook(
                    onHoursChange = { audioBookHour = it },
                    onMinutesChange = { audioBookMinute = it },
                    onSecondsChange = { audioBookSecond = it },
                    hours = audioBookHour,
                    minutes = audioBookMinute,
                    seconds = audioBookSecond)
            }
            Input(
                placeholder = stringResource(R.string.Enter_a_note),
                minLines = 5,
                maxLines = 5,
                onTextChange = { bookNote = it },
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

@Composable
fun LocationInAudioBook(
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    paddingBetweenItems: Dp = 8.dp,
    hours: Int = 0,
    minutes: Int = 0,
    seconds: Int = 0,
) {

    val inputModifier = Modifier.padding(horizontal = paddingBetweenItems / 2)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Input(
            modifier = inputModifier,
            title = stringResource(R.string.hours_short),
            defaultValue = hours.toString(),
            onTextChange = {
                onHoursChange(it.toIntOrNull() ?: 0)
            },
            maxCharacters = 3,
            inputType = InputType.Number,
        )
        Input(
            modifier = inputModifier,
            title = stringResource(R.string.minute_short),
            defaultValue = minutes.toString(),
            onTextChange = { onMinutesChange(it.toIntOrNull() ?: 0) },
            maxCharacters = 3,
            inputType = InputType.Number,
        )
        Input(
            modifier = inputModifier,
            title = stringResource(R.string.seconds_short),
            defaultValue = seconds.toString(),
            onTextChange = { onSecondsChange(it.toIntOrNull() ?: 0) },
            maxCharacters = 3,
            inputType = InputType.Number,
        )
    }
}

@Composable
fun LocationInBook(
    onBookPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    bookPage: Int = 0,
) {
    Input(
        modifier = modifier,
        placeholder = "Enter the page of the note",
        onTextChange = { onBookPageChange(it.toInt()) },
        defaultValue = bookPage.toString(),
        maxCharacters = 4,
        inputType = InputType.Number,
    )
}