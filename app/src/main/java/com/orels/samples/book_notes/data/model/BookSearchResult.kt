package com.orels.samples.book_notes.data.model

import com.google.gson.annotations.SerializedName
import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.Books

typealias Items = List<Item>

data class BookSearchResult(
    @SerializedName("kind") val kind: String,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("items") var items: Items,
) {
    fun toBooks(): Books = items.map {
        it.toBook()
    }
}

class Item(
    @SerializedName("kind") val kind: String,
    @SerializedName("id") val id: String,
    @SerializedName("selfLink") val selfLink: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo,
) {
    fun toBook(): Book = Book(
        title = volumeInfo.title,
        authors = volumeInfo.authors,
        publishedDate = volumeInfo.publishedDate,
        description = volumeInfo.description,
        pageCount = volumeInfo.pageCount,
        categories = volumeInfo.categories,
        smallThumbnail = volumeInfo.imageLinks.smallThumbnail,
        thumbnail = volumeInfo.imageLinks.thumbnail
    )
}

data class VolumeInfo(
    @SerializedName("title") var title: String,
    @SerializedName("authors") var authors: List<String>,
    @SerializedName("publishedDate") var publishedDate: String,
    @SerializedName("description") var description: String,
    @SerializedName("pageCount") var pageCount: Int,
    @SerializedName("categories") var categories: List<String>,
    @SerializedName("imageLinks") var imageLinks: ImageLinks,
)

data class ImageLinks(
    @SerializedName("smallThumbnail") var smallThumbnail: String,
    @SerializedName("thumbnail") var thumbnail: String,
)