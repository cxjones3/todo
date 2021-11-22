package com.example.todoApp.repo

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.HeaderViewListAdapter
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.todoApp.model.*
import com.example.todoApp.repo.remote.RetrofitInstance
import kotlinx.coroutines.flow.first
import retrofit2.Response
import retrofit2.http.HeaderMap
//import java.util.prefs.Preferences

object LoginRepo {
    val Context.dataStore by preferencesDataStore("credentials")
    //lateinit var token : String
    val headers= HashMap<String, String>()//.also { it["Authorization"] = token }

    //suspend fun syncData(endpoint : String, token : String)=RetrofitInstance.syncService.sync(endpoint, token)
    suspend fun login(credentials : LoginBody) : Response<RegisterResponse> = RetrofitInstance.syncService.login(credentials)
    suspend fun register(credentials : RegisterBody)= RetrofitInstance.syncService.register(credentials)
    suspend fun getList() = RetrofitInstance.syncService.getAll(headers)
    suspend fun add(newTodo : TodoBody) = RetrofitInstance.syncService.addTodo(headers, newTodo)
    suspend fun delete(id : Double):Response<Any> = RetrofitInstance.syncService.delete(headers, id)
    suspend fun update( id : Double, edit: Todo): Response<Todo> = RetrofitInstance.syncService.update(headers, id, edit)

    suspend fun checkCredentials(application : Application, token3: String)// : List<Todo>
    {
     //   lateinit var gets : Response<List<Todo>>

        val dataStore = application.applicationContext.dataStore

       // val test = login(credentials)
       // Log.d("Login attempt:",test.body().toString())
       // if(test.body() == null){

      //  }else{
           // var token = test.body()?.token!!
           // headers["Authorization"] = token3
        dataStoreSave(dataStore, token3)
        dataStoreRead(dataStore)
         //   dataStore.edit { it[stringPreferencesKey("auth")] = test.body()?.token!! }
         //   dataStoreRead(dataStore)
          //  val reg3 = TodoBody("piaodf")
           // add(reg3)
            //gets = getList()

       // return gets.body()!!
    }

    private suspend fun dataStoreSave(ds : DataStore<Preferences>, token : String)
    {
        ds.edit{
            //it[intPreferencesKey("size")]= 4
            it[stringPreferencesKey("auth")]=token}
    }

    private suspend fun dataStoreRead(ds : DataStore<Preferences>)
    {
        val preferences = ds.data.first()
        ds.edit{
            headers["Authorization"] = preferences[stringPreferencesKey("auth")]!!
            Log.d("LOGIC", headers["Authorization"]!!)}


    }


}