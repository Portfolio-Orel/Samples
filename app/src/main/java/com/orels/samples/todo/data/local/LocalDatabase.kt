package com.orels.samples.todo.data.local

import androidx.room.RoomDatabase
import com.orels.samples.todo.data.local.dao.TodoDao
import com.orels.samples.todo.domain.model.Task

@androidx.room.Database(entities = [Task::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun taskDao(): TodoDao
}