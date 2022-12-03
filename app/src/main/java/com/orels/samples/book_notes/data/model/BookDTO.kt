package com.orels.samples.book_notes.data.model

import com.orels.samples.book_notes.domain.model.Book


fun Book.toInsert(): Map<String, Any> = mapOf(
    "title" to title,
    "isActive" to isActive,
    "authors" to (authors ?: emptyList()),
    "publishedDate" to (publishedDate ?: ""),
    "description" to (description ?: ""),
    "pageCount" to (pageCount ?: 0),
    "categories" to (categories ?: emptyList()),
    "smallThumbnail" to (smallThumbnail ?: ""),
    "thumbnail" to (thumbnail ?: ""),
)

fun Book.toUpdate(): Map<String, Any> = mapOf(
    "title" to title,
    "isActive" to isActive,
    "authors" to (authors ?: emptyList()),
    "publishedDate" to (publishedDate ?: ""),
    "description" to (description ?: ""),
    "pageCount" to (pageCount ?: 0),
    "categories" to (categories ?: emptyList()),
    "smallThumbnail" to (smallThumbnail ?: ""),
    "thumbnail" to (thumbnail ?: ""),
)

fun Book.toDelete(): Map<String, Any> = mapOf(
    "isActive" to false
)

fun fromMapBook(map: Map<String, Any>, id: String): Book = Book(
    id = id,
    title = map["title"] as? String ?: "",
    authors = map["authors"] as? List<String> ?: emptyList(),
    publishedDate = map["publishedDate"] as? String ?: "",
    description = map["description"] as? String ?: "",
    pageCount = map["pageCount"] as? Int ?: 0,
    categories = map["categories"] as? List<String> ?: emptyList(),
    smallThumbnail = map["smallThumbnail"] as? String ?: "",
    thumbnail = map["thumbnail"] as? String ?: "",
    isActive = map["isActive"] as? Boolean ?: true
)