package com.newstore.favqs.local.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newstore.favqs.data.local.AppDatabase
import com.newstore.favqs.data.local.UserDao
import com.newstore.favqs.data.models.response.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class UserDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    private fun getDummyUser() =
        User(userId = 0, name = "dummyUser", email = "dummyEmail", token = "dummyToken")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun test_insertUserInDatabase() {
        runBlocking {
            // Given
            val givenUser = getDummyUser()

            // Invoke
            userDao.insert(givenUser)

            // Assert
            MatcherAssert.assertThat(
                database.userDao().getLoggedInUser(),
                CoreMatchers.equalTo(givenUser)
            )
        }
    }
}