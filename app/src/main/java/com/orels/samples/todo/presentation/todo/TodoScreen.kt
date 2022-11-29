package com.orels.samples.todo.presentation.todo

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.samples.todo.domain.model.Task

@Composable
fun TodoScreen(viewModel: TodoViewModel = hiltViewModel()) {
    val state = viewModel.state
    Column(modifier = Modifier
        .background(Color.Red)
        .scrollable(ScrollState(0), orientation = Orientation.Vertical)
    ) {
        TodoList(tasks = state.tasks,
            isLoading = state.isLoading,
            error = state.error,
            onAddTask = { task ->
                viewModel.onEvent(TodoEvent.AddTask(task))
            },
            onRemoveTask = { task ->
                viewModel.onEvent(TodoEvent.RemoveTask(task))
            },
            onTaskUpdate = { task ->
                viewModel.onEvent(TodoEvent.UpdateTask(task))
            })
        Button(onClick = {
            viewModel.onEvent(TodoEvent.AddTask(task = Task(id = "",
                title = "New Task",
                description = "New Task Description",
                isDone = false)))
        }) {
            Text(
                text = "Add Task",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun TodoList(
    tasks: List<Task>,
    isLoading: Boolean,
    error: String,
    onAddTask: (Task) -> Unit,
    onRemoveTask: (Task) -> Unit,
    onTaskUpdate: (Task) -> Unit,
) {
    LazyRow {
        items(tasks.size) { index ->
            val task = tasks[index]
            TodoItem(
                task = task,
                onRemoveTask = { onRemoveTask(task) },
                onTaskUpdate = { onTaskUpdate(task) }
            )
        }
    }
}

@Composable
fun TodoItem(
    task: Task,
    onRemoveTask: () -> Unit,
    onTaskUpdate: (Task) -> Unit,
) {
    Row {
        Text(
            text = "X",
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onRemoveTask()
                }
        )
        Text(
            text = task.title ?: "",
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onTaskUpdate(task.copy(isDone = !task.isDone))
                }
        )
    }
}