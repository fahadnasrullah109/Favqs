package com.newstore.favqs.common

object SecureKeys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun baseUrl(): String
    external fun authorizationToken(): String
}