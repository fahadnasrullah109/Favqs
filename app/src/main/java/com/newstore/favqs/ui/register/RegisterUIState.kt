package com.newstore.favqs.ui.register

sealed class RegisterUIState {
    object InvalidUsernameState : RegisterUIState()
    object InvalidEmailState : RegisterUIState()
    object InvalidPasswordState : RegisterUIState()
    object LoadingState : RegisterUIState()
    object SuccessState : RegisterUIState()
    class ErrorState(val message: String?) : RegisterUIState()
}