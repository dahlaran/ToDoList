package com.dahlaran.todolist.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.utilis.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalTaskSource constructor(private val taskDao: TaskDao) : BaseTaskSource {

    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun observeTasks(): LiveData<DataResult<List<Task>>> {
        return taskDao.observeTasks().map { result ->
            DataResult.Success(result)
        }
    }

    override fun observeTask(taskId: String): LiveData<DataResult<Task>> {
        return taskDao.observeTask(taskId).map {
            DataResult.Success(it)
        }
    }

    override suspend fun getTasks(): DataResult<List<Task>> = withContext(coroutineDispatcher) {
        return@withContext try {
            DataResult.Success(taskDao.getTasks())
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun insert(task: Task) = withContext(coroutineDispatcher) {
        taskDao.insert(task)
    }

    override suspend fun updateTask(task: Task) = withContext(coroutineDispatcher) {
        taskDao.update(task)
    }

    override suspend fun completeTask(taskId: String) = withContext(coroutineDispatcher) {
        taskDao.updateCompleted(taskId, true)
    }

    override suspend fun activateTask(taskId: String) = withContext(coroutineDispatcher) {
        taskDao.updateCompleted(taskId, false)
    }

    override suspend fun deleteTask(task: Task) = withContext(coroutineDispatcher) {
        taskDao.delete(task)
    }

    override suspend fun deleteTask(taskId: String) {
        taskDao.delete(taskId)
    }

    override suspend fun deleteAllTasks() = withContext(coroutineDispatcher) {
        taskDao.deleteAllTasks()
    }

    override suspend fun getTask(taskId: String): DataResult<Task> = withContext(coroutineDispatcher) {
        return@withContext try {
            DataResult.Success(taskDao.getTask(taskId))
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

}