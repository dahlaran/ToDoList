package com.dahlaran.todolist.views.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dahlaran.todolist.R
import com.dahlaran.todolist.databinding.FragmentTaskListBinding
import com.dahlaran.todolist.viewmodels.TasksViewModel
import com.dahlaran.todolist.views.tasks.adapter.TaskAdapterDecoration
import com.dahlaran.todolist.views.tasks.adapter.TasksListAdapter
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : Fragment() {

    private val viewModel by viewModels<TasksViewModel>()

    private lateinit var viewDataBinding: FragmentTaskListBinding

    private lateinit var listAdapter: TasksListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        addTaskFloatingButton.setOnClickListener {
            findNavController().navigate(
                TaskListFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                    null,
                    resources.getString(R.string.add_task)
                )
            )
        }

        viewModel.loadTask()

        setUpListAdapter()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentTaskListBinding.inflate(inflater, container, false).apply {
            tasksViewModel = viewModel
        }

        return viewDataBinding.root
    }

    private fun setUpListAdapter() {

        val viewModel = viewDataBinding.tasksViewModel
        if (viewModel != null) {
            listAdapter = TasksListAdapter(viewModel) { itemClicked ->
                findNavController().navigate(TaskListFragmentDirections.actionTasksFragmentToTaskDetailFragment(itemClicked.id))
            }
            viewDataBinding.tasksList.addItemDecoration(TaskAdapterDecoration(context))
            viewDataBinding.tasksList.adapter = listAdapter
        } else {
            Log.e(javaClass.simpleName, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

}