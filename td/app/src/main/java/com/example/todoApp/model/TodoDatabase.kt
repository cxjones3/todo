package com.example.todoApp.model

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoApp.repo.TodoDao

@Database(entities = [Todor::class],version = 1)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun todoDao() : TodoDao

    companion object {
        const val DATABASE_NAME = "todo_database"
        private lateinit var application: Application
        private val database: TodoDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(application, TodoDatabase::class.java, DATABASE_NAME).build()
        }


        fun getDatabase(application2: Application): TodoDatabase {
            this.application = application2
            return database
        }
    }
}