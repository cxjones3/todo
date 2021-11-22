package com.example.todoApp.repo.remote

import com.example.todoApp.model.*
import com.example.todoApp.model.Body
import retrofit2.Response
import retrofit2.http.*


interface SyncService {
   // @Headers("Authorization:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWJqZWN0IjoidXNlciIsImlkIjoyLCJ1c2VybmFtZSI6IkJsdWVVc2VyIiwiZW1haWwiOiJibHVldXNlckBnbWFpbC5jb20iLCJpYXQiOjE2MzcxNzMwNzYsImV4cCI6MTY2ODczMDY3Nn0.moJmAY2dciAhjqSsDs5M7z_x28fCu8MsuPRAZK5a4Oo")

    @POST("api/auth/register")
    suspend fun register(
        @retrofit2.http.Body() body: RegisterBody
    ): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(
        @retrofit2.http.Body() body: LoginBody
    ): Response<RegisterResponse>

    //@Headers("Authorization:{token}")
    @GET("api/todos")
    suspend fun getAll(
        @HeaderMap headers: Map<String,String>
    ): Response<List<Todo>>

   // @Headers("Authorization:{token}")
    @POST("api/todos")
    suspend fun addTodo(
       @HeaderMap headers: Map<String,String>,
        @retrofit2.http.Body() body: TodoBody
    ): Response<Todo>


    @GET("api/todos/{endpoint}")
    suspend fun sync(
        @HeaderMap headers: Map<String,String>,
        @Path("endpoint") endpoint : Double,
    ): Response<Todo>

    @PUT("api/todos/{endpoint}")
    suspend fun update(
        @HeaderMap headers: Map<String,String>,
        @Path("endpoint") endpoint : Double,
        @retrofit2.http.Body() body: Todo,
    ): Response<Todo>

    @DELETE("api/todos/{endpoint}")
    suspend fun delete(
        @HeaderMap headers: Map<String,String>,
        @Path("endpoint") endpoint : Double,
    ): Response<Any>

}