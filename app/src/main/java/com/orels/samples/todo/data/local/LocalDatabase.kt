package com.orels.samples.todo.data.local

import androidx.room.RoomDatabase
import com.orels.samples.todo.data.local.dao.TodoDao
import com.orels.samples.todo.domain.model.Todo

@androidx.room.Database(entities = [Todo::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}