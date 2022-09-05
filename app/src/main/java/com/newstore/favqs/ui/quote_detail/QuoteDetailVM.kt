package com.newstore.favqs.ui.quote_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newstore.favqs.data.models.response.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteDetailVM @Inject constructor() : ViewModel() {

    private var quote: Quote? = null
    private val _uiState = MutableLiveData<QuoteDetailUIState>()
    val uiState: LiveData<QuoteDetailUIState> = _uiState

    fun setQuote(quote: Quote?) {
        this.quote = quote
    }

    fun getQuote() {
        quote?.let {
            _uiState.value = QuoteDetailUIState.ContentState(it)
        }
    }

}