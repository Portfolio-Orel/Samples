package com.orels.samples.book_notes.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.orels.samples.book_notes.domain.model.BookLocation

class Converters {
    @TypeConverter
    fun stringToBookLocation(string: String?): BookLocation =
        Gson().fromJson(string, BookLocation::class.java)

    @TypeConverter
    fun bookLocationToString(bookLocation: BookLocation): String =
        Gson().toJson(bookLocation)
}