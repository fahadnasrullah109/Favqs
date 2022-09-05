package com.newstore.favqs.data.models.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuoteResponse(
    val last_page: Boolean,
    val page: Int,
    val quotes: List<Quote>
) : Parcelable

@Parcelize
@Entity
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val author: String?,
    val author_permalink: String,
    val body: String,
    val favorites_count: Int,
    val tags: List<String>,
    val upvotes_count: Int
) : Parcelable