package com.newstore.favqs.data.usecases

import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.request.QuoteRequest
import com.newstore.favqs.data.models.response.QuoteResponse
import com.newstore.favqs.data.models.response.User
import com.newstore.favqs.data.repositories.QuoteRepository
import com.newstore.favqs.data.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetQuotesUsecase(
    private val quoteRepository: QuoteRepository,
    private val dispatcher: CoroutineDispatcher
) : FlowUseCase<QuoteRequest, DataResource<QuoteResponse>>(dispatcher) {

    override fun execute(parameters: QuoteRequest) = quoteRepository.getQuotes(parameters)
}