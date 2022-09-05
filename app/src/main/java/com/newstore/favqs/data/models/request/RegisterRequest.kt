package com.newstore.favqs.data.models.request

class RegisterRequest(
    val user: RegisterUser
)

data class RegisterUser(
    val login: String,
    val email: String,
    val password: String
)