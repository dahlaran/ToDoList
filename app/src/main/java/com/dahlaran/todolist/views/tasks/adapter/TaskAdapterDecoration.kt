package com.dahlaran.todolist.views.tasks.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.dahlaran.todolist.R


class TaskAdapterDecoration(context: Context?) : ItemDecoration() {

    private val divider: Drawable? = context?.let { ContextCompat.getDrawable(it, R.drawable.task_decoration_drawable) }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        divider?.let {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            val childCount: Int = parent.childCount
            for (i in 0 until childCount) {
                val child: View = parent.getChildAt(i)
                val childLayoutParams: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
                val bottom: Int = child.bottom + childLayoutParams.bottomMargin
                val top: Int = bottom - it.intrinsicHeight
                // Draw separator inside child
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)

                if (i == 0) {
                    val secondTop = child.top - childLayoutParams.topMargin
                    divider.setBounds(left, secondTop, right, secondTop + it.intrinsicHeight)
                    divider.draw(c)
                }
            }
        }
    }
}