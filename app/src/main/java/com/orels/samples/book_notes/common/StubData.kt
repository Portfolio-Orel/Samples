package com.orels.samples.book_notes.common

import com.orels.samples.book_notes.domain.model.Book
import com.orels.samples.book_notes.domain.model.BookNote

object StubData {
    // a list called books of 10 different books of Book object
    val books = listOf(
        Book(
            id = "1",
            title = "The Lord of the Rings",
//            author = "J. R. R. Tolkien",
//            description = "The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien's 1937 fantasy novel The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling novels ever written, with over 150 million copies sold.",
//            imageUrl = "https://upload.wikimedia.org/wikipedia/en/8/87/Ringstrilogyposter.jpg",
            isActive = true
        ),
        Book(
            id = "2",
            title = "Harry Potter and the Philosopher's Stone",
//            author = "J. K. Rowling",
//            description = "Harry Potter and the Philosopher's Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling's debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. Harry makes close friends and a few enemies during his first year at the school, and with the help of his friends, Harry faces an attempted comeback by the dark wizard Lord Voldemort, who killed Harry's parents, but failed to kill Harry when he was just 15 months old.",
//            imageUrl = "https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg",
            isActive = true
        ),
        Book(
            id = "3",
            title = "The Hobbit",
//            author = "J. R. R. Tolkien",
//            description = "The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction. The book remains popular and is recognized as a classic in children's literature.",
//            imageUrl = "https://upload.wikimedia.org/wikipedia/en/b/b4/The_Hobbit_-_first_edition.jpg",
        )
    )

    // A list of 20 book notes of object BookNote using the books list randomly
    val bookNotes = (1..20).map {
        BookNote(
            id = "$it",
            bookId = books.random().id,
            title = "Title $it",
            note = "Note $it",
            page = 43,
            createdAt = System.currentTimeMillis(),
            isActive = true
        )
    }

}