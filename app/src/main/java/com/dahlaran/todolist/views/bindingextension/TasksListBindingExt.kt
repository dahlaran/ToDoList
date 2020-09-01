package com.dahlaran.todolist.views.bindingextension

import android.content.res.ColorStateList
import android.graphics.Paint
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dahlaran.todolist.R
import com.dahlaran.todolist.data.Task
import com.dahlaran.todolist.views.tasks.adapter.TasksListAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Task>?) {
    items?.let {
        (listView.adapter as TasksListAdapter).submitList(items)
    }
}

@BindingAdapter("app:completedTask")
fun setCompletedTask(textView: TextView, enabled: Boolean) {
    if (enabled) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

fun EditText.setValidity(valid: Boolean) {
    if (valid) {
        this.backgroundTintList = null
    } else {
        this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.editTextColorWrong))
    }
}