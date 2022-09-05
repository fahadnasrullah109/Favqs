package com.newstore.favqs.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
    @SerializedName("login")
    val name: String?,
    @SerializedName("User-Token")
    val token: String
) : Parcelable