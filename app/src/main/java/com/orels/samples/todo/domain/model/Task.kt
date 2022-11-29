package com.orels.samples.todo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class Task(
    var id: String = "",
    var title: String? = null,
    var description: String? = null,
    var isDone: Boolean = false,
    @ColumnInfo(defaultValue = "1") var isActive: Boolean = true,
) {
    fun isNullOrEmpty(): Boolean = id.isBlank()
}