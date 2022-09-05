package com.newstore.favqs.data.remote.interceptors

import com.newstore.favqs.common.SecureKeys
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Authorization", SecureKeys.authorizationToken()).build()
        return chain.proceed(request)
    }
}