package com.newstore.favqs.ui.quote_detail

import com.newstore.favqs.data.models.response.Quote

sealed class QuoteDetailUIState {
    class ContentState(val quote: Quote) : QuoteDetailUIState()
}