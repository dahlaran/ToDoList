package com.dahlaran.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.data.TaskRepository
import com.dahlaran.todolist.utilis.DataResult
import kotlinx.coroutines.launch

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    val dataLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val tasks: LiveData<List<Task>> = observeTask()

    val empty: LiveData<Boolean> = Transformations.map(tasks) {
        it.isEmpty()
    }

    private fun observeTask(): LiveData<List<Task>> {
        dataLoading.value = true

        viewModelScope.launch {
            TaskRepository.refreshTasks()
            dataLoading.value = false
        }
        return TaskRepository.observeTasks().switchMap {liveList ->
            when (liveList) {
                is DataResult.Success -> {
                    MutableLiveData(liveList.data)
                }
                is DataResult.Error -> {
                    Log.e(javaClass.simpleName, liveList.throwable.message ?: "ERROR : No message")
                    MutableLiveData()
                }
                else -> {
                    MutableLiveData()
                }
            }
        }
    }

    fun loadTask() {
        observeTask()
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            TaskRepository.completeTask(task)
        } else {
            TaskRepository.activateTask(task)
        }
    }
}