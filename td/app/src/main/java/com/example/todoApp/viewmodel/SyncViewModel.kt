package com.example.todoApp.viewmodel

import android.util.Log
import com.example.todoApp.repo.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


import android.app.Application
import androidx.lifecycle.*
import com.example.todoApp.model.*
import com.example.todoApp.repo.TodoRepo
import kotlinx.coroutines.flow.first
import java.lang.IllegalArgumentException

class SyncViewModel(application: Application,
private val todoRepo: TodoRepo
) : AndroidViewModel(application) {

    val todoSet = todoRepo.getAll()
        .asLiveData(viewModelScope.coroutineContext+Dispatchers.Default)

    val t = application
    //val allTodos =liveData{emit(LoginRepo.checkCredentials(t, credentials))}

    suspend fun viewEdit(todo: Todo){ todoRepo.add(convert(todo)) }

    suspend fun viewAdd(todo: Todo){todoRepo.add(convert(todo))}

    suspend fun viewDel(todo: Todo){todoRepo.delete(convert(todo))}


    fun syncData(){
        viewModelScope.launch(Dispatchers.IO) {
            val allData = todoRepo.getAll().first()
            Log.d("updating...",allData.size.toString())
            for(index in 0 until allData.size){
                val updated = LoginRepo.syncData(convertFrom(allData[index]).id)
                if (updated.body() != null){
                    Log.d("updating...",updated.body()!!.title)
                    todoRepo.add(convert(updated.body()!!))
                }
                else
                    Log.d("nully bully",updated.toString())
            }
        }
    }


    companion object{
      lateinit var credentials : LoginBody

        fun convert(todo: Todo) : Todor{
            var converted = Todor(todo.id,
                todo.title,
                todo.description ?: "n/a",
                todo.completed,
                todo.userId,
                todo.date,
                todo.updatedAt)
            return converted
        }

        fun convertFrom(todo: Todor) : Todo{
            var converted = Todo(todo.id,
                todo.title,
                todo.description ,
                todo.completed,
                todo.userId,
                todo.date,
                todo.updatedAt)
            return converted
        }

    }

    class Factory(
        private val application: Application,
        private val todoRepo: TodoRepo
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SyncViewModel::class.java)) {
                Log.d("VM","doing this")
                return SyncViewModel(application, todoRepo) as T
            } else {
                throw IllegalArgumentException("Cannot create instance of recipeViewModel")
            }
        }
    }



}