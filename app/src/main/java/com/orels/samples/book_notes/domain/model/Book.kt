package com.orels.samples.book_notes.domain.model

import androidx.room.Entity

typealias Books = List<Book>

@Entity(primaryKeys = ["id"])
data class Book(
    var id: String,
    var title: String,
    var isActive: Boolean = true,
)