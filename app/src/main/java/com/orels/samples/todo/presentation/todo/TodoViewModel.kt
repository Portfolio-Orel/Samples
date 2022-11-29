package com.orels.samples.todo.presentation.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.samples.todo.domain.interactor.TodoInteractor
import com.orels.samples.todo.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val interactor: TodoInteractor,
) : ViewModel() {
    var state by mutableStateOf(TodoState())
        private set

    init {
        getTodos()
    }

    fun onEvent(event: TodoEvent) {
        when (event) {
            is TodoEvent.AddTask -> addTask(event.task)
            is TodoEvent.RemoveTask -> removeTask(event.task)
            is TodoEvent.UpdateTask -> updateTask(event.task)
            else -> {}
        }
    }

    private fun getTodos() {
        state = state.copy(isLoading = true)
        interactor.getAll().observeOn(AndroidSchedulers.mainThread()).subscribe({ tasks ->
                state = state.copy(tasks = tasks.getOrNull() ?: emptyList(), isLoading = false)
            }, { error ->
                state = state.copy(error = error.message ?: "", isLoading = false)
            })
    }

    private fun addTask(task: Task) {
        interactor.insert(task).observeOn(AndroidSchedulers.mainThread()).subscribe({
                state = state.copy(tasks = state.tasks + task)
            }, { error ->
                state = state.copy(error = error.message ?: "", isLoading = false)
            })
    }

    private fun removeTask(task: Task) {
        interactor.delete(task).observeOn(AndroidSchedulers.mainThread()).subscribe({
                state = state.copy(tasks = state.tasks - task)
            }, { error ->
                state = state.copy(error = error.message ?: "", isLoading = false)
            })
    }

    private fun updateTask(task: Task) {
        interactor.update(task).observeOn(AndroidSchedulers.mainThread()).subscribe({
                state = state.copy(tasks = state.tasks.map { if (it.id == task.id) task else it })
            }, { error ->
                state = state.copy(error = error.message ?: "", isLoading = false)
            })
    }
}