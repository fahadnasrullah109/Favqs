package com.newstore.favqs.data.models.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    var userId: Long = 0,
    @SerializedName("login")
    val name: String?,
    val email: String?,
    @SerializedName("User-Token")
    val token: String?
) : Parcelable