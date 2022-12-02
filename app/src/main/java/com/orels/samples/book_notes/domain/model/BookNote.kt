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
    var location: BookLocation = BookLocation(page = 0),
    var createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(defaultValue = "1") var isActive: Boolean = true,
) {
    fun isNullOrEmpty(): Boolean = id.isBlank()
}

class BookLocation(
    var page: Long = 0,
    var hours: Long = 0,
    var minutes: Long = 0,
    var seconds: Long = 0,
) {
    constructor(page: Int) : this(page.toLong())
    constructor(hours: Int, minutes: Int, seconds: Int) : this(hours.toLong(), minutes.toLong(), seconds.toLong())
}