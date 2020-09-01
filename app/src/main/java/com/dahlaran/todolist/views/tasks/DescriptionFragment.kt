package com.dahlaran.todolist.views.tasks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dahlaran.todolist.R
import com.dahlaran.todolist.databinding.FragmentDescriptionBinding
import com.dahlaran.todolist.viewmodels.DescriptionTaskViewModel
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentDescriptionBinding
    private val args: DescriptionFragmentArgs by navArgs()

    private val viewModel by viewModels<DescriptionTaskViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editTaskFloatingButton.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionTaskDetailFragmentToAddEditTaskFragment(
                    viewModel.taskId.value,
                    resources.getString(R.string.edit_task)
                )
            )
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)

        viewDataBinding = FragmentDescriptionBinding.bind(view).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.taskId)

        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuDelete -> {
                viewModel.deleteTask { findNavController().navigate(DescriptionFragmentDirections.actionTaskDetailFragmentToTasksFragment()) }
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.description_fragment_menu, menu)
    }
}