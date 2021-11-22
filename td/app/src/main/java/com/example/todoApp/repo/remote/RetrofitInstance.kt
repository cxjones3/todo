package com.example.todoApp.repo.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val SyncRetro : Retrofit = Retrofit.Builder()
        .baseUrl("https://knex-todo.herokuapp.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val syncService : SyncService = SyncRetro.create(SyncService::class.java)

}