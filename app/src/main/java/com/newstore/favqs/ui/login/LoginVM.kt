package com.newstore.favqs.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstore.favqs.common.Validator
import com.newstore.favqs.data.models.request.LoginRequest
import com.newstore.favqs.data.models.request.User
import com.newstore.favqs.data.onError
import com.newstore.favqs.data.onLoading
import com.newstore.favqs.data.onSuccess
import com.newstore.favqs.data.usecases.LoginUsecase
import com.newstore.favqs.data.usecases.SaveUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val validator: Validator,
    private val loginUsecase: LoginUsecase,
    private val saveUserUsecase: SaveUserUsecase
) : ViewModel() {

    private val _uiState = MutableLiveData<LoginUIState>()
    val uiState: LiveData<LoginUIState> = _uiState

    fun login(email: String, password: String) {
        if (validator.isValidEmail(email).not()) {
            _uiState.value = LoginUIState.InvalidEmailState
        } else if (validator.isValidPassword(password).not()) {
            _uiState.value = LoginUIState.InvalidPasswordState
        } else {
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUsecase.invoke(LoginRequest(User(login = email, password = password)))
                .collect { dataResource ->
                    dataResource.onLoading {
                        _uiState.value = LoginUIState.LoadingState
                    }.onSuccess {
                        if (this.data.token == null) {
                            _uiState.value = LoginUIState.ErrorState("Invalid User or Password")
                        } else {
                            saveUserInDb(this.data)
                            return@onSuccess
                        }
                    }.onError {
                        _uiState.value = LoginUIState.ErrorState(this.exception.message)
                    }
                }
        }
    }

    private fun saveUserInDb(user: com.newstore.favqs.data.models.response.User) {
        viewModelScope.launch {
            saveUserUsecase.invoke(user).collect {
                _uiState.value = LoginUIState.SuccessState
            }
        }
    }
}