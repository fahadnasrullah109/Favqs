package com.newstore.favqs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newstore.favqs.data.models.response.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User where userId = 0")
    fun getLoggedInUserAsync(): Flow<User?>

    @Query("SELECT * FROM User where userId = 0")
    fun getLoggedInUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Query("DELETE FROM User")
    fun clear()

}