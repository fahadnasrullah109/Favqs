package com.newstore.favqs.ui.login

sealed class LoginUIState {
    object InvalidEmailState : LoginUIState()
    object InvalidPasswordState : LoginUIState()
    object LoadingState : LoginUIState()
    object SuccessState : LoginUIState()
    class ErrorState(val message: String?) : LoginUIState()
}