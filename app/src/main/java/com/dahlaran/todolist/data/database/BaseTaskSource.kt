package com.dahlaran.todolist.data.database

import androidx.lifecycle.LiveData
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.utilis.DataResult

interface BaseTaskSource {

    // Get Task
    fun observeTasks(): LiveData<DataResult<List<Task>>>
    fun observeTask(taskId: String): LiveData<DataResult<Task>>
    suspend fun getTasks(): DataResult<List<Task>>
    suspend fun getTask(taskId: String): DataResult<Task>

    // Add Task
    suspend fun insert(task: Task)

    // Modify Task
    suspend fun updateTask(task: Task)
    suspend fun completeTask(taskId: String)

    suspend fun activateTask(taskId: String)

    // Delete Task
    suspend fun deleteTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
}