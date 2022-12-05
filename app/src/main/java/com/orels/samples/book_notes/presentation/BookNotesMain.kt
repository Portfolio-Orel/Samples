package com.orels.samples.book_notes.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orels.samples.book_notes.common.Screen
import com.orels.samples.book_notes.presentation.add_book.AddBookScreen
import com.orels.samples.book_notes.presentation.book_notes.BookNotesScreen

@Composable
fun BookNotesMain() {
    val navHostController = rememberNavController()
    val navController = navHostController as NavController

    NavHost(
        navController = navHostController, startDestination = Screen.Main.route,
    ) {
        // Main Screen with spring animation
        composable(
            route = Screen.Main.route,
        ) {
            BookNotesScreen(navController = navController)
        }

        composable(
            route = Screen.AddBook.route,
        ) {
            AddBookScreen(navController = navController)
        }
    }
}