package com.orels.samples.book_notes.presentation.book_notes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.orels.extension.divideByGoldenRatio
import com.orels.samples.R
import com.orels.samples.book_notes.domain.model.Book

@Composable
fun BookRowComponent(
    book: Book,
    onAddBook: (Book) -> Unit,
    onRemoveBook: (Book) -> Unit,
    isSaved: Boolean = false,
    rowHeight: Int = 100,
) {
    val authors = book.authors?.joinToString(", ")
    val iconModifier = Modifier
        .padding(horizontal = 8.dp)
        .size(32.dp)

    Row(modifier = Modifier
        .height(rowHeight.dp)
        .fillMaxWidth()) {
        BookImage(modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(rowHeight.dp)
            .width(rowHeight.dp.divideByGoldenRatio()),
            thumbnail = book.thumbnail ?: "")
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = authors ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = book.publishedDate ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
            )
        }
        Spacer(Modifier.weight(1f))
        Box(modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center) {
            if (isSaved) {
                Icon(imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.book_added),
                    modifier = iconModifier
                        .clickable {
                            onRemoveBook(book)
                        },
                    tint = MaterialTheme.colorScheme.error
                )
            } else {
                Icon(imageVector = Icons.Outlined.AddCircle,
                    contentDescription = stringResource(R.string.add_book),
                    modifier = iconModifier
                        .clickable {
                            onAddBook(book)
                        },
                    tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun BookImage(thumbnail: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        var isLoading by remember { mutableStateOf(true) }
        var isImageLoaded by remember { mutableStateOf(false) }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .listener { request: ImageRequest, result: SuccessResult ->
                    isImageLoaded = request.data == result.drawable
                    isLoading = false
                }
                .build(),
            contentDescription = stringResource(R.string.a_thumbnail_of_the_book),
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(Color.Transparent),
        )
        if (isLoading && isImageLoaded.not()) {
            Icon(
                modifier = Modifier.zIndex(2f),
                painter = painterResource(id = R.drawable.ic_book_placeholder),
                contentDescription = stringResource(
                    R.string.book_image_placeholder),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            )
        }
    }
}