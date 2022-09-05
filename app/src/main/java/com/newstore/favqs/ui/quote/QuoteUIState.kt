package com.newstore.favqs.ui.quote

import com.newstore.favqs.data.models.response.Quote

sealed class QuoteUIState {
    object LoadingState : QuoteUIState()
    class SuccessState(val quote : Quote) : QuoteUIState()
    class ErrorState(val message: String?) : QuoteUIState()
}