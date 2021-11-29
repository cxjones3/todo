package com.example.todoApp.util

import android.view.LayoutInflater
import android.view.ViewGroup

val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

fun String.sayCletis() = this+"Cletis"