package com.example.todoApp.repo

import android.app.Application
import com.example.todoApp.model.TodoDatabase
import com.example.todoApp.model.Todor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TodoRepo(private val application: Application) {
    private val todoDao by lazy{
       TodoDatabase.getDatabase(application).todoDao()
    }

    fun getAll() = todoDao.getAllTodos().flowOn(Dispatchers.IO)

    suspend fun add(todo : Todor){
        withContext(Dispatchers.IO){
            todoDao.insertTodo(todo)
        }
    }

    suspend fun delete(todo: Todor){
        withContext(Dispatchers.IO){
            todoDao.deleteTodo(todo)
        }
    }
}