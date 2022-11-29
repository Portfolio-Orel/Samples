package com.orels.samples.todo.presentation.todo

import com.orels.samples.todo.domain.model.Todo

data class TodoState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList(),
    val error: String = ""
)

sealed class TodoEvent {
    object GetTodos: TodoEvent()
}