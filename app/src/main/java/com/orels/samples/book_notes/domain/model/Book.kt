package com.orels.samples.book_notes.domain.model

import androidx.room.Entity
import com.orels.components.DropdownItem

typealias Books = List<Book>

@Entity(primaryKeys = ["id"])
data class Book(
    var id: String = "",
    var title: String = "",
    var authors: List<String>? = emptyList(),
    var publishedDate: String? = "",
    var description: String? = "",
    var pageCount: Int? = 0,
    var categories: List<String>? = emptyList(),
    var smallThumbnail: String? = "",
    var thumbnail: String? = "",
    var isActive: Boolean = true
) : DropdownItem {
    override fun getIdentifier(): String {
        return id
    }

    override fun getValue(): String {
        return title
    }
}