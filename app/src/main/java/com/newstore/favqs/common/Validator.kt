package com.newstore.favqs.common

import android.util.Patterns

class Validator {
    fun isValidEmail(email: String) = when {
        email.isBlank() || email.isEmpty() -> false
        Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> false
        else -> true
    }

    fun isValidPassword(password: String) = when {
        password.isBlank() || password.isEmpty() || password.length < 5 -> false
        else -> true
    }
}