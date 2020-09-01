package com.dahlaran.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.data.TaskRepository
import com.dahlaran.todolist.utilis.DataResult
import kotlinx.coroutines.launch

class DescriptionTaskViewModel(application: Application) : AndroidViewModel(application) {
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val taskId = MutableLiveData<String>()

    val task: LiveData<Task?> = taskId.switchMap { taskId ->
        TaskRepository.observeTask(taskId).map { taskResult ->
            if (taskResult is DataResult.Success) {
                taskResult.data
            } else {
                Log.e(javaClass.simpleName, "ERROR : Value observed task is null")
                null
            }
        }
    }

    val isDataAvailable: LiveData<Boolean> = task.map { it != null }

    fun start(taskId: String?) {
        dataLoading.value = false
        // If we're already loading or already loaded, return (maybe remove)
        if (dataLoading.value == true || taskId == this.taskId.value) {
            return
        }
        // Force task's loading
        this.taskId.value = taskId
    }


    fun setCompleted(completed: Boolean) = viewModelScope.launch {
        val task = task.value ?: return@launch

        if (completed) {
            TaskRepository.completeTask(task)
        } else {
            TaskRepository.activateTask(task)
        }
    }

    fun deleteTask(callbackQuit: () -> Unit) {
        task.value?.let {
            viewModelScope.launch {
                TaskRepository.deleteTask(it.id)
                // Quit description page when task deleted
                callbackQuit()
            }
        } ?: Log.e(javaClass.simpleName, "ERROR : value of current task is null for deleteTask()")
    }

    fun refresh() {
        // Refresh the repository and the task will be updated automatically.
        task.value?.let {
            dataLoading.value = true
            viewModelScope.launch {
                TaskRepository.refreshTask(it.id)
                dataLoading.value = false
            }
        } ?: Log.e(javaClass.simpleName, "ERROR : value of current task is null for refresh()")
    }
}