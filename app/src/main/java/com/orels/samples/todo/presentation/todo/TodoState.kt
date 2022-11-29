package com.orels.samples.todo.presentation.todo

import com.orels.samples.todo.domain.model.Task

data class TodoState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val error: String = ""
)

sealed class TodoEvent {
    object GetTodos: TodoEvent()
    data class AddTask(val task: Task): TodoEvent()
    data class RemoveTask(val task: Task): TodoEvent()
    data class UpdateTask(val task: Task): TodoEvent()
}