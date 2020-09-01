package com.dahlaran.todolist.utilis

class Verification {
    companion object {
        /**
         * Check if a task can be created by using the title and the description given
         * @param title of the task
         * @param description of the task
         *
         * @return 0 -> title and description is valid for a new task, 1 -> Title is incorrect, 2 -> description is incorrect
         */
        fun newTaskVerification(title: String, description: String): Int {
            if (title.isEmpty()) {
                return 1
            } else if (description.isEmpty()) {
                return 2
            }
            return 0
        }
    }
}