package com.orels.samples.book_notes.common

import androidx.annotation.StringRes
import com.orels.samples.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Main : Screen("main", R.string.main)
    object AddBook : Screen("add_book", R.string.add_book)

    fun withArgs(vararg args: String?): String =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg ?: ""}")
            }
        }
}
