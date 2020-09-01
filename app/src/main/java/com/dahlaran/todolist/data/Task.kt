package com.dahlaran.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    var title: String?,
    var content: String?,
    val isCompleted: Boolean,
    @PrimaryKey val id: String = UUID.randomUUID().toString()){

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isNullOrEmpty() || content.isNullOrEmpty()
}