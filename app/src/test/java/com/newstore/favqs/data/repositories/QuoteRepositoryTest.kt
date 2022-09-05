package com.newstore.favqs.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newstore.favqs.TestingCoroutineRule
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.local.QuoteDao
import com.newstore.favqs.data.models.request.QuoteRequest
import com.newstore.favqs.data.models.response.QuoteResponse
import com.newstore.favqs.data.remote.RetroApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuoteRepositoryTest {

    // Subject under Test
    private lateinit var repository: QuoteRepository

    @MockK
    private lateinit var apiService: RetroApiService

    @MockK
    private lateinit var quoteDao: QuoteDao

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }


    @Test
    fun `test getQuotes, gives Success`() = runBlocking {
        // Given
        repository = QuoteRepositoryImpl(apiService, quoteDao, coroutinesRule.testDispatcher)
        val quotesResponse = getDummyQuoteResponse()

        // When
        coEvery { apiService.getQuotes(any()) }
            .returns(quotesResponse)
        coEvery { quoteDao.clear() }.returns(Unit)
        coEvery { quoteDao.insert(any()) }.returns(Unit)
        coEvery { quoteDao.getQuotes() }.returns(quotesResponse.quotes)

        // Invoke
        val apiResponseFlow = repository.getQuotes(getDummyQuoteRequest())

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val response = apiResponseFlow.last()
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            response,
            CoreMatchers.instanceOf(DataResource.Success::class.java)
        )

        coVerify(exactly = 1) { apiService.getQuotes(any()) }
        confirmVerified(apiService)
    }

    private fun getDummyQuoteResponse() =
        QuoteResponse(last_page = false, page = 1, quotes = listOf())

    private fun getDummyQuoteRequest() = QuoteRequest(pageNo = 1)
}