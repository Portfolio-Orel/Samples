package com.orels.samples.book_notes.data.model

import com.orels.samples.book_notes.domain.model.Book


fun Book.toInsert(): Map<String, Any> = mapOf(
    "title" to title,
    "isActive" to isActive
)

fun Book.toUpdate(): Map<String, Any> = mapOf(
    "title" to title,
    "isActive" to isActive
)

fun Book.toDelete(): Map<String, Any> = mapOf(
    "isActive" to false
)

fun fromMapBook(map: Map<String, Any>, id: String): Book = Book(
    id = id,
    title = map["title"] as String,
    isActive = map["isActive"] as Boolean
)