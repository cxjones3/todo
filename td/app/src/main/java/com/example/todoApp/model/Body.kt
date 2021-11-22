package com.example.todoApp.model

import com.squareup.moshi.JsonClass

class Body {
}

@JsonClass(generateAdapter = true)
data class RegisterBody(
    val username: String,
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val message: String,
    val token: String,
)

@JsonClass(generateAdapter = true)
data class LoginBody(
    val username: String,
    val password: String,
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val username: String,
    val password: String,
)

@JsonClass(generateAdapter = true)
data class TodoBody(
    val title: String,
)