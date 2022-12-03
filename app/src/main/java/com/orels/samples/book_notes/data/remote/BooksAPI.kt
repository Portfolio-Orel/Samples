package com.orels.samples.book_notes.data.remote

import com.orels.samples.book_notes.data.model.BookSearchResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface BooksAPI {
    // Search book function that uses google's api and retrofit which allows a dynamic search
    // @param searchText the text to search
    // @return a list of BookSearchResult
    // @author Orel Zilberman
    @GET("books/v1/volumes")
    fun searchBook(
        @Query("q") searchText: String,
        @Query("max-results") maxResults: Int = 10,
    ): Single<BookSearchResult>


}