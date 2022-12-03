package com.orels.samples.book_notes.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orels.samples.book_notes.domain.model.BookLocation

class Converters {

    private val stringsListType = object : TypeToken<List<String>?>() {}.type

    @TypeConverter
    fun stringToBookLocation(string: String?): BookLocation =
        Gson().fromJson(string, BookLocation::class.java)

    @TypeConverter
    fun bookLocationToString(bookLocation: BookLocation): String =
        Gson().toJson(bookLocation)

    @TypeConverter
    fun stringToListOfStrings(string: String?): List<String> =
        Gson().fromJson(string, stringsListType)

    @TypeConverter
    fun listOfStringsToString(strings: List<String>): String =
        Gson().toJson(strings)

}