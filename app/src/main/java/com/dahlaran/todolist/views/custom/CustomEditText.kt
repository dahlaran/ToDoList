package com.dahlaran.todolist.views.custom

import android.content.Context
import android.util.AttributeSet
import com.dahlaran.todolist.views.bindingextension.setValidity
import com.google.android.material.textfield.TextInputEditText


class CustomEditText : TextInputEditText {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var isTextValid = false

    fun setValid(valid: Boolean) {
        isTextValid = valid
        setValidity(valid)
    }
}
