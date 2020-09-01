package com.dahlaran.todolist.views.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.databinding.LayoutTaskBinding
import com.dahlaran.todolist.utilis.difference.TaskDifference
import com.dahlaran.todolist.viewmodels.TasksViewModel

class TasksListAdapter(private val viewModel: TasksViewModel, private val onclickItemCallback: (itemClicked: Task) -> Unit) :
    ListAdapter<Task, TasksListAdapter.TasksViewHolder>(TaskDifference()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder.from(parent, onclickItemCallback)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class TasksViewHolder(private val binding: LayoutTaskBinding, private val onclickCallback: ((itemClicked: Task) -> Unit)) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: TasksViewModel, item: Task) {
            // Set information to layout
            binding.viewmodel = viewModel
            binding.task = item
            binding.executePendingBindings()

            // Set on click listener to layout to trigger an event (opening of task description)
            binding.taskMainLinear.setOnClickListener {
                onclickCallback(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup, onclickItemCallback: (itemClicked: Task) -> Unit): TasksViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutTaskBinding.inflate(layoutInflater, parent, false)

                return TasksViewHolder(binding, onclickItemCallback)
            }
        }
    }


}