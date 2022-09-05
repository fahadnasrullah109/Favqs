package com.newstore.favqs.ui.quotelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.newstore.favqs.TestingCoroutineRule
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.request.QuoteRequest
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.data.models.response.QuoteResponse
import com.newstore.favqs.data.usecases.GetQuotesUsecase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuoteListVMUnitTest {

    // Subject under Test
    private lateinit var viewModel: QuoteListVM

    @MockK
    private lateinit var getQuotesUsecase: GetQuotesUsecase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestingCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when loadQuotes invoked on QuoteVM, get Quotes from server`() = runBlocking {
        // Given
        val response = getDummyQuoteResponse()
        val uiObserver = mockk<Observer<QuoteListUIState>>(relaxed = true)

        // When
        coEvery { getQuotesUsecase.invoke(getDummyRequest()) }
            .returns(flowOf(DataResource.Success(response)))

        // Invoke
        viewModel = QuoteListVM(getQuotesUsecase)
        viewModel.loadQuotes(isFromSearch = false, query = "")
        viewModel.uiState.observeForever(uiObserver)

        // Then
        coVerify(exactly = 1) { getQuotesUsecase.invoke(any()) }
        verify { uiObserver.onChanged(ofType(QuoteListUIState.SuccessState::class))}
    }

    private fun getDummyRequest() = QuoteRequest(pageNo = 1, filter = null)
    private fun getDummyQuoteResponse() =
        QuoteResponse(last_page = false, page = 1, quotes = listOf(Quote(id = 1, author = "dummyAuthor", author_permalink = "link", body = "dummyBody", favorites_count = 1, tags = listOf("tags"), upvotes_count = 0)))
}