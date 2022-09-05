package com.newstore.favqs.ui.quotelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstore.favqs.data.models.request.QuoteRequest
import com.newstore.favqs.data.onError
import com.newstore.favqs.data.onLoading
import com.newstore.favqs.data.onSuccess
import com.newstore.favqs.data.usecases.GetQuotesUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteListVM @Inject constructor(private val getQuotesUsecase: GetQuotesUsecase) :
    ViewModel() {

    private val _uiState = MutableLiveData<QuoteListUIState>()
    val uiState: LiveData<QuoteListUIState> = _uiState

    private var currentPage = 0

    fun loadQuotes(isFromSearch: Boolean, query: String = "") {
        if (isFromSearch && query.isBlank()) {
            return
        }
        viewModelScope.launch {
            val request = QuoteRequest(++currentPage, if (isFromSearch) query else null)
            getQuotesUsecase.invoke(request).collect { dataResource ->
                dataResource.onLoading {
                    _uiState.value = QuoteListUIState.LoadingState
                }.onSuccess {
                    currentPage = this.data.page
                    _uiState.value = QuoteListUIState.SuccessState(this.data.quotes)
                }.onError {
                    _uiState.value = QuoteListUIState.ErrorState(this.exception.message)
                }
            }
        }
    }

}