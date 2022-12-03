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
    var isActive: Boolean = true,
) : DropdownItem {
    override fun getIdentifier(): String {
        return id
    }

    override fun getValue(): String {
        return title
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Book) {
            return false
        }
        return title == other.title
                && publishedDate == other.publishedDate
                && authors == other.authors
                && pageCount == other.pageCount
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (authors?.hashCode() ?: 0)
        result = 31 * result + (publishedDate?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (pageCount ?: 0)
        result = 31 * result + (categories?.hashCode() ?: 0)
        result = 31 * result + (smallThumbnail?.hashCode() ?: 0)
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        result = 31 * result + isActive.hashCode()
        return result
    }
}