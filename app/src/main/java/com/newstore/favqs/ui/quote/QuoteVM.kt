package com.newstore.favqs.ui.quote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstore.favqs.data.onError
import com.newstore.favqs.data.onLoading
import com.newstore.favqs.data.onSuccess
import com.newstore.favqs.data.usecases.GetRandomQuoteUsecase
import com.newstore.favqs.ui.quotelist.QuoteListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteVM @Inject constructor(private val getRandomQuoteUsecase: GetRandomQuoteUsecase) :
    ViewModel() {
    private val _uiState = MutableLiveData<QuoteUIState>()
    val uiState: LiveData<QuoteUIState> = _uiState



    init {
        loadRandomQuote()
    }

    private fun loadRandomQuote() {
        viewModelScope.launch {
           getRandomQuoteUsecase.invoke(Unit).collect {
                   dataResource ->
               dataResource.onLoading {
                   _uiState.value = QuoteUIState.LoadingState
               }.onSuccess {
                   _uiState.value = QuoteUIState.SuccessState(this.data)
               }.onError {
                   _uiState.value = QuoteUIState.ErrorState(this.exception.message)
               }
           }
        }
    }
}