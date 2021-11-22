package com.example.todoApp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoData" )
data class Todor (
    @PrimaryKey()val id: Double,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description")val description : String,
    @ColumnInfo(name = "completed")val completed : Boolean,
    @ColumnInfo(name = "userID")val userId: Double,
    @ColumnInfo(name = "date")val date: Int,
    @ColumnInfo(name = "updatedAt")val updatedAt: Int,
)