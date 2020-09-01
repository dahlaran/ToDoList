package com.dahlaran.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.data.TaskRepository
import com.dahlaran.todolist.utilis.DataResult
import com.dahlaran.todolist.utilis.Verification
import kotlinx.coroutines.launch

class AddTasksViewModel(application: Application) : AndroidViewModel(application) {
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private var taskId: String? = null
    private var isNewTask: Boolean = false
    private var isDataLoaded = false
    private var taskCompleted = false


    fun start(taskId: String?) {
        if (dataLoading.value == true) {
            return
        }

        this.taskId = taskId
        if (taskId == null) {
            // No need to populate, it's a new task
            isNewTask = true
            return
        }
        if (isDataLoaded) {
            // No need to populate, already have data.
            return
        }

        launchGetRequest(taskId)
    }

    private fun launchGetRequest(taskId: String) {
        isNewTask = false
        dataLoading.value = true

        viewModelScope.launch {
            TaskRepository.getTask(taskId).let { result ->
                if (result is DataResult.Success) {
                    onTaskLoaded(result.data)
                } else {
                    onDataNotAvailable()
                }
            }
        }
    }

    private fun onTaskLoaded(task: Task) {
        title.value = task.title
        description.value = task.content
        taskCompleted = task.isCompleted
        dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        dataLoading.value = false
    }

    /**
     * Check if a task can be created by using the title and the description given
     *
     * @param callbackQuit when the task is correct and have been saved
     *
     * @return 0 -> title and description is valid for a new task, 1 -> Title is incorrect, 2 -> description is incorrect
     */

    fun saveTask(callbackQuit: () -> Unit): Int {
        val currentTitle = title.value ?: ""
        val currentDescription = description.value ?: ""

        when (Verification.newTaskVerification(currentTitle, currentDescription)) {
            1 -> return 1
            2 -> return 2
        }

        val currentTaskId = taskId
        if (isNewTask || currentTaskId == null) {
            viewModelScope.launch {
                createTask(Task(currentTitle, currentDescription, false))
                callbackQuit()
            }

        } else {
            viewModelScope.launch {
                updateTask(Task(currentTitle, currentDescription, taskCompleted, currentTaskId))
                callbackQuit()
            }
        }
        return 0
    }

    private fun createTask(newTask: Task) = viewModelScope.launch {
        TaskRepository.saveTask(newTask)
    }

    private fun updateTask(task: Task) {
        if (isNewTask) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        viewModelScope.launch {
            TaskRepository.saveTask(task)
        }
    }
}