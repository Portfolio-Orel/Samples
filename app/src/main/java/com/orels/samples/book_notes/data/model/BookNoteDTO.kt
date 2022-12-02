package com.orels.samples.book_notes.data.model

import com.orels.samples.book_notes.domain.model.BookLocation
import com.orels.samples.book_notes.domain.model.BookNote

fun BookNote.toInsert(): Map<String, Any> = mapOf(
    "bookId" to bookId,
    "title" to title,
    "location" to location.toInsert(),
    "isActive" to isActive
)

fun BookNote.toUpdate(): Map<String, Any> = mapOf(
    "bookId" to bookId,
    "title" to title,
    "location" to location.toUpdate(),
    "isActive" to isActive
)

fun BookNote.toDelete(): Map<String, Any> = mapOf(
    "isActive" to false
)

fun fromMapBookNote(map: Map<String, Any>, id: String): BookNote = BookNote(
    id = id,
    bookId = map["bookId"] as String,
    title = map["title"] as String,
    location = BookLocation().fromMap(map["location"] as? Map<String, Any> ?: emptyMap()),
    isActive = map["isActive"] as Boolean
)

fun BookLocation.toInsert(): Map<String, Any> = mapOf(
    "page" to page,
    "hours" to hours,
    "minutes" to minutes,
    "seconds" to seconds
)

fun BookLocation.toUpdate(): Map<String, Any> = mapOf(
    "page" to page,
    "hours" to hours,
    "minutes" to minutes,
    "seconds" to seconds
)

fun BookLocation.fromMap(map: Map<String, Any>): BookLocation = BookLocation(
    page = map["page"] as Long,
    hours = map["hours"] as Long,
    minutes = map["minutes"] as Long,
    seconds = map["seconds"] as Long
)