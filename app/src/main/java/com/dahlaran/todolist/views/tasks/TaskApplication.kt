package com.dahlaran.todolist.views.tasks

import android.app.Application

class TaskApplication :Application(){

    companion object {
        lateinit var instance: TaskApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}