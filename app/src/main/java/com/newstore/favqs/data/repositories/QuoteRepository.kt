package com.newstore.favqs.data.repositories

import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.callApi
import com.newstore.favqs.data.data
import com.newstore.favqs.data.local.QuoteDao
import com.newstore.favqs.data.models.request.QuoteRequest
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.data.models.response.QuoteResponse
import com.newstore.favqs.data.remote.RetroApiService
import com.newstore.favqs.data.succeeded
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface QuoteRepository {
    fun getQuotes(parameters: QuoteRequest): Flow<DataResource<QuoteResponse>>
    fun getRandomQuote(): Flow<DataResource<Quote>>
}

class QuoteRepositoryImpl(
    private val apiService: RetroApiService,
    private val quoteDao: QuoteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : QuoteRepository {

    override fun getQuotes(parameters: QuoteRequest): Flow<DataResource<QuoteResponse>> = flow {
        emit(DataResource.Loading)
        val result = callApi {
            apiService.getQuotes(parameters.pageNo, parameters.filter)
        }
        if (result.succeeded()) {
            saveQuotes(result)
        }
        val cachedQuotes = quoteDao.getQuotes()
        if (cachedQuotes.isNotEmpty()) {
            emit(
                DataResource.Success(
                    QuoteResponse(
                        last_page = false,
                        page = 1,
                        quotes = quoteDao.getQuotes()
                    )
                )
            )
        } else {
            emit(result)
        }

    }.flowOn(dispatcher)

    override fun getRandomQuote(): Flow<DataResource<Quote>> = flow {
        emit(DataResource.Loading)
        val quote = quoteDao.getRandomQuote()
        if (quote == null) {
            val result = callApi {
                apiService.getQuotes(1)
            }
            if (result.succeeded()) {
                saveQuotes(result)
                emit(
                    DataResource.Success(
                        quoteDao.getRandomQuote()!!
                    )
                )
            } else {
                emit(DataResource.Error<Throwable>(RuntimeException("Not Found")))
            }
        } else {
            emit(DataResource.Success(quote))
        }
    }

    private fun saveQuotes(result: DataResource<QuoteResponse>) {
        // Clear Quotes Table and insert new quotes
        quoteDao.clear()
        quoteDao.insert(result.data?.quotes ?: listOf())
    }


}