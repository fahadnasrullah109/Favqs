package com.newstore.favqs.local.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newstore.favqs.data.local.AppDatabase
import com.newstore.favqs.data.local.QuoteDao
import com.newstore.favqs.data.models.response.Quote
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
class QuoteDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var quoteDao: QuoteDao

    private fun getDummyQuotes() =
        listOf(getDummyQuote(1), getDummyQuote(2))

    private fun getDummyQuote(id: Int) = Quote(
        id = id,
        author = "dummayAuthor",
        author_permalink = "dummyLink",
        body = "dummyQuote",
        favorites_count = 1,
        tags = listOf(),
        upvotes_count = 1
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        quoteDao = database.quoteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun test_insertQuotesInDatabase() {
        runBlocking {
            // Given
            val givenQuotes = getDummyQuotes()

            // Invoke
            quoteDao.insert(givenQuotes)

            // Assert
            MatcherAssert.assertThat(
                database.quoteDao().getQuotes(),
                CoreMatchers.equalTo(givenQuotes)
            )
        }
    }
}