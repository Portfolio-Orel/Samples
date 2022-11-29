package com.orels.samples.todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey var id: String,
    var title: String,
    var description: String,
    var isDone: Boolean
)