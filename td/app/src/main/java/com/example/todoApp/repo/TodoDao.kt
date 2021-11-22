package com.example.todoApp.repo

import androidx.room.*
import com.example.todoApp.model.Todo
import com.example.todoApp.model.Todor
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao{

    @Query("SELECT * FROM todoData")
    fun getAllTodos(): Flow<List<Todor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todor)

    @Insert
    fun insertAllTodos(vararg todo: Todor)

    @Delete
    fun deleteTodo(todo: Todor)
}