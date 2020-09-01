package com.dahlaran.todolist.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dahlaran.todolist.data.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun observeTasks(): LiveData<List<Task>>

    @Query("SELECT  *   FROM Task WHERE id = :taskId")
    fun observeTask(taskId: String): LiveData<Task>

    @Query("SELECT * FROM Task")
    fun getTasks(): List<Task>

    @Query("SELECT * FROM Task WHERE id = :taskId")
    fun getTask(taskId: String): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("UPDATE task SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM Task WHERE id = :taskId")
    suspend fun delete(taskId: String)

    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()
}