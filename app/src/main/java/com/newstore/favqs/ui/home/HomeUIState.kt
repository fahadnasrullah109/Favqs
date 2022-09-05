package com.newstore.favqs.ui.home

sealed class HomeUIState {
    object UserLoggedOutState : HomeUIState()
    class ErrorState(val message: String?) : HomeUIState()
}