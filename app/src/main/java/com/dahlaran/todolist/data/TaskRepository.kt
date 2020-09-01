package com.dahlaran.todolist.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.dahlaran.todolist.data.database.BaseTaskSource
import com.dahlaran.todolist.data.database.LocalTaskSource
import com.dahlaran.todolist.data.database.TaskDatabase
import com.dahlaran.todolist.utilis.DataResult
import com.dahlaran.todolist.views.tasks.TaskApplication
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object TaskRepository {

    // Local source
    private val localTaskSource: BaseTaskSource = LocalTaskSource(TaskDatabase.getInstance(TaskApplication.instance.applicationContext).taskDao())

    suspend fun refreshTask(id: String) {
        // Do network call to get remote task instead of source call
        val remoteTask = localTaskSource.getTask(id)

        if (remoteTask is DataResult.Success) {
            localTaskSource.insert(remoteTask.data)
        }
    }

    suspend fun refreshTasks() {
        // Do network call to get remote tasks instead of source call
        val remoteTasks = localTaskSource.getTasks()

        if (remoteTasks is DataResult.Success) {
            // Delete all old tasks
            localTaskSource.deleteAllTasks()
            // Save all tasks
            remoteTasks.data.forEach { task ->
                localTaskSource.insert(task)
            }
        } else if (remoteTasks is DataResult.Error) {
            Log.e(javaClass.simpleName, remoteTasks.throwable.message ?: "ERROR : No message")
        }
    }

    fun observeTasks(): LiveData<DataResult<List<Task>>> {
        return localTaskSource.observeTasks()
    }

    fun observeTask(taskId: String): LiveData<DataResult<Task>> {
        return localTaskSource.observeTask(taskId)
    }

    suspend fun getTask(taskId: String): DataResult<Task> {
        return localTaskSource.getTask(taskId)
    }

    suspend fun saveTask(task: Task) {
        localTaskSource.insert(task)
    }

    suspend fun completeTask(task: Task) {
        localTaskSource.completeTask(task.id)
    }

    suspend fun activateTask(task: Task) {
        localTaskSource.activateTask(task.id)
    }

    suspend fun deleteTask(taskId: String) {
        coroutineScope {
            launch { localTaskSource.deleteTask(taskId) }
        }
    }
}