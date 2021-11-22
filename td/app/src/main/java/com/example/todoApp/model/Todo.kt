package com.example.todoApp.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Todo(
    var id : Double,
    var title : String,
    var description : String?,
    var completed : Boolean,
    var userId : Double,
    var date : Int,
    var updatedAt : Int
):Parcelable