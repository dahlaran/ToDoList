package com.dahlaran.todolist.utilis.difference

import androidx.recyclerview.widget.DiffUtil
import com.dahlaran.todolist.data.Task

class TaskDifference : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}