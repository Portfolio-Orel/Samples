package com.orels.samples.book_notes.presentation.book_notes.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
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
    rowHeight: Int = 100,
) {
    val authors = book.authors?.joinToString(", ")

    Row(modifier = Modifier
        .height(rowHeight.dp)
        .fillMaxWidth()) {
        BookImage(modifier = Modifier
            .height(rowHeight.dp)
            .width(rowHeight.dp.divideByGoldenRatio()),
            thumbnail = book.thumbnail ?: "")
        Column(
            modifier = Modifier.padding(8.dp),
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

@Preview
@Composable
fun BookRowComponentPreview() {
    BookRowComponent(
        book = Book(
            id = "1",
            title = "The Lord of the Rings",
            authors = listOf("J.R.R. Tolkien"),
            publishedDate = "1954",
            description = "The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien's 1937 fantasy novel The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling novels ever written, with over 150 million copies sold.",
            pageCount = 1216,
            categories = listOf("Fantasy"),
            smallThumbnail = "http://books.google.com/books/content?id=4g4BAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
            thumbnail = "http://books.google.com/books/content?id=4g4BAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
            isActive = true
        )
    )
}