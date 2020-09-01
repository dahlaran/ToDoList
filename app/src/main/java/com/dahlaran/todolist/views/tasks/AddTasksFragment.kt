package com.dahlaran.todolist.views.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dahlaran.todolist.R
import com.dahlaran.todolist.databinding.FragmentNewTaskBinding
import com.dahlaran.todolist.viewmodels.AddTasksViewModel
import kotlinx.android.synthetic.main.fragment_new_task.*

class AddTasksFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentNewTaskBinding
    private val viewModel by viewModels<AddTasksViewModel>()
    private val args: AddTasksFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_new_task, container, false)
        viewDataBinding = FragmentNewTaskBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.start(args.taskId)

        saveTaskFloatingButton.setOnClickListener {
            val saveStatus =
                viewModel.saveTask { findNavController().navigate(AddTasksFragmentDirections.actionAddEditTaskFragmentToTasksFragment()) }

            context?.let {
                when (saveStatus) {
                    1 -> {
                        Toast.makeText(it, "Title text is not valid", Toast.LENGTH_SHORT).show()
                        addTaskTitleEditText.setValid(false)
                        addTaskDescriptionEditText.setValid(true)
                    }
                    2 -> {
                        Toast.makeText(it, "Description text is not valid", Toast.LENGTH_SHORT).show()
                        addTaskTitleEditText.setValid(true)
                        addTaskDescriptionEditText.setValid(false)
                    }
                    else -> {
                        addTaskTitleEditText.setValid(true)
                        addTaskDescriptionEditText.setValid(true)
                    }
                }
            }
        }
    }
}