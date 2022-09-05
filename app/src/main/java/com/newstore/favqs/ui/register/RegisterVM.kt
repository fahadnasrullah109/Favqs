package com.newstore.favqs.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstore.favqs.common.Validator
import com.newstore.favqs.data.models.request.RegisterRequest
import com.newstore.favqs.data.models.request.RegisterUser
import com.newstore.favqs.data.onError
import com.newstore.favqs.data.onLoading
import com.newstore.favqs.data.onSuccess
import com.newstore.favqs.data.usecases.RegisterUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val validator: Validator,
    private val registerUsecase: RegisterUsecase
) : ViewModel() {

    private val _uiState = MutableLiveData<RegisterUIState>()
    val uiState: LiveData<RegisterUIState> = _uiState

    fun register(username: String, email: String, password: String) {
        if (isValidUsername(username).not()) {
            _uiState.value = RegisterUIState.InvalidUsernameState
        } else if (validator.isValidEmail(email).not()) {
            _uiState.value = RegisterUIState.InvalidEmailState
        } else if (validator.isValidPassword(password).not()) {
            _uiState.value = RegisterUIState.InvalidPasswordState
        } else {
            registerUser(username, email, password)
        }
    }

    private fun isValidUsername(username: String) = when {
        username.isBlank() || username.isEmpty() || username.length < 5 -> false
        else -> true
    }

    private fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            registerUsecase.invoke(
                RegisterRequest(
                    RegisterUser(
                        login = username,
                        email = email,
                        password = password
                    )
                )
            ).collect { dataResource ->
                dataResource.onLoading {
                    _uiState.value = RegisterUIState.LoadingState
                }.onSuccess {
                    if (this.data.token == null) {
                        _uiState.value = RegisterUIState.ErrorState(null)
                    } else {
                        _uiState.value = RegisterUIState.SuccessState
                    }
                }.onError {
                    _uiState.value = RegisterUIState.ErrorState(this.exception.message)
                }
            }
        }
    }
}