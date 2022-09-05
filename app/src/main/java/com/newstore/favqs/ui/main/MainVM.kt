package com.newstore.favqs.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstore.favqs.base.BaseUIState
import com.newstore.favqs.data.usecases.GetLoggedInUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val loggedInUserUsecase: GetLoggedInUserUsecase) :
    ViewModel() {
    private val _uiState = MutableLiveData<BaseUIState>()
    val uiState: LiveData<BaseUIState> = _uiState

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            loggedInUserUsecase.invoke(Unit).collect { user ->
                if (user == null) {
                    _uiState.value = BaseUIState.ErrorState("No logged-in user found.")
                } else {
                    _uiState.value = BaseUIState.ContentState
                }
            }
        }
    }
}