package com.newstore.favqs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstore.favqs.data.DataResource
import com.newstore.favqs.data.usecases.LogoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val logoutUsecase: LogoutUsecase) : ViewModel() {

    private val _uiState = MutableLiveData<HomeUIState>()
    val uiState: LiveData<HomeUIState> = _uiState

    fun logUserOut() {
        viewModelScope.launch {
            logoutUsecase.invoke(Unit).collect { dataSource ->
                when (dataSource) {
                    DataResource.Empty, DataResource.Loading -> {}
                    is DataResource.Success -> {
                        _uiState.value = HomeUIState.UserLoggedOutState
                    }
                    is DataResource.Error<*> -> {
                        _uiState.value = HomeUIState.ErrorState("Can't logout, please try again.")
                    }
                }
            }
        }
    }
}