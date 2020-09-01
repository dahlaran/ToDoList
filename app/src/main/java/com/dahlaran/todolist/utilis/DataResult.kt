package com.dahlaran.todolist.utilis

/**
 * LiveData can now return an error if it fail
 * Check Succeeded if it failed or not
 */
sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val throwable: Throwable) : DataResult<Nothing>()
}

// TODO: REMOVE TO SEE
val DataResult<*>.succeeded get() = this is DataResult.Success<*> && data != null