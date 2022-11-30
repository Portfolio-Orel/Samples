package com.orels.samples.book_notes.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey


typealias BookNotes = List<BookNote>

/**
 * A book note
 * @param id the id of the book note
 * @param bookId the id of the book
 * @param note the note of the book note
 * @param page the page of the book note
 * @param createdAt the date the book note was created
 * @param isActive the status of the book note
 */
@Entity(primaryKeys = ["id"],
    foreignKeys = [ForeignKey(entity = Book::class,
        parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = ForeignKey.NO_ACTION)]
)
data class BookNote(
    var id: String = "",
    var bookId: String = "",
    var title: String = "",
    var note: String = "",
    var page: Int? = null,
    var createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(defaultValue = "1") var isActive: Boolean = true,
) {
    fun isNullOrEmpty(): Boolean = id.isBlank()
}