package com.orels.samples.todo.presentation.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(TodoState())
        private set

    fun onEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.GetTodos -> {
                state = state.copy(isLoading = true)
            }
        }
    }
}