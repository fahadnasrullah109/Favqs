package com.newstore.favqs.data.models.request

data class LoginRequest(
    val user: User
)

data class User(
    val login: String,
    val password: String
)