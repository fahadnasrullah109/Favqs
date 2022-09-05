package com.newstore.favqs.ui.quotelist

import com.newstore.favqs.data.models.response.Quote

sealed class QuoteListUIState {
    object LoadingState : QuoteListUIState()
    class SuccessState(val quotes: List<Quote>) : QuoteListUIState()
    class ErrorState(val message: String?) : QuoteListUIState()
}