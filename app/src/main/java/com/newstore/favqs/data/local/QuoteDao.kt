package com.newstore.favqs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.data.models.response.User
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote")
    fun getQuotes(): List<Quote>

    @Query("SELECT * FROM quote ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuote(): Quote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quotes: List<Quote>)

    @Query("DELETE FROM quote")
    fun clear()

}