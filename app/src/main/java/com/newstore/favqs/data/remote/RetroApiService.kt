package com.newstore.favqs.data.remote

import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.request.RegisterRequest
import com.newstore.favqs.data.models.response.QuoteResponse
import com.newstore.favqs.data.models.response.RegisterResponse
import com.newstore.favqs.data.models.response.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetroApiService {

    @POST("session")
    suspend fun login(@Body request: LoginRequest): User

    @POST("users")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") pageNo: Int,
        @Query("filter") filter: String? = null
    ): QuoteResponse

}