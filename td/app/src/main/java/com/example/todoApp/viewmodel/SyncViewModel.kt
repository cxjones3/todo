package com.example.todoApp.viewmodel

import android.util.Log
import com.example.todoApp.repo.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.todoApp.databinding.EditLayoutBinding
import com.example.todoApp.model.*
import com.example.todoApp.repo.TodoDao
import com.example.todoApp.repo.TodoRepo
import com.example.todoApp.view.ComposeFragment
import com.example.todoApp.view.EditFragment
import com.example.todoApp.view.EditFragmentDirections
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.IllegalArgumentException
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

//import androidx.lifecycle.liveData


class SyncViewModel(application: Application,
private val todoRepo: TodoRepo
) : AndroidViewModel(application) {

    val todoSet = todoRepo.getAll()
        .asLiveData(viewModelScope.coroutineContext+Dispatchers.Default)

    //fun add

    val t = application
    //val allTodos =liveData{emit(LoginRepo.checkCredentials(t, credentials))}
    //val allTodos2 = LoginRepo.checkCredentials(t, credentials).asLiveData(viewModelScope.coroutineContext+Dispatchers.Default)
  //  val delTodo = liveData{emit(LoginRepo.delete(modTodo.id)) }
    val editTodo = liveData{emit(LoginRepo.update(modTodo.id, modTodo)) }
    //val createTodo = liveData{emit(LoginRepo.add(create)) }

    fun viewEdit(){
        viewModelScope.launch(Dispatchers.IO) {val editResponse = LoginRepo.update(modTodo.id, modTodo)
            Log.d("testing create",editResponse.toString())
            ComposeFragment.messenger = "u"+editResponse.message()
            if (editResponse.message().equals("OK")){
                todoRepo.add(convert(editResponse.body()!!))
            }
        }
    }

    fun viewAdd(){
        viewModelScope.launch(Dispatchers.IO) {val createTodo = LoginRepo.add(create)
            Log.d("testing create",createTodo.toString())
            ComposeFragment.messenger = "a"+createTodo.message()
            if (createTodo.message().equals("OK")){
                todoRepo.add(convert(createTodo.body()!!))
            }
        }
    }

    fun viewDel(){
        viewModelScope.launch(Dispatchers.IO) {val delResponse = LoginRepo.delete(modTodo.id)
            Log.d("testing delete",delResponse.toString())
            ComposeFragment.messenger = "d"+delResponse.message()
            if (delResponse.message().equals("OK")){
                todoRepo.delete(convert(modTodo))
            }
        }
    }

    fun viewLogin(){
        viewModelScope.launch(Dispatchers.IO) {val afterLog = LoginRepo.login(credentials)
            Log.d("testing login",afterLog.toString())
            ComposeFragment.messenger = "l"+afterLog.message()
            if (afterLog.message().equals("OK")){
                LoginRepo.checkCredentials(getApplication(),afterLog.body()!!.token)
            }
        }
    }



val reg = RegisterBody("username : cj2","email : cj2@gmail.com","password : pass")

val reg4 = RegisterBody("BlueUser3","blueuser@gmail.com","password123")
val reg5 = LoginBody("BlueUser3","password123")

    companion object{
      lateinit var credentials : LoginBody
      lateinit var modTodo : Todo
      lateinit var create : TodoBody
      var messenger = "no"
      val piza = 6

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