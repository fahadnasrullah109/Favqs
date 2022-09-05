package com.newstore.favqs.base

sealed class BaseUIState {
    object LoadingState : BaseUIState()
    object ContentState : BaseUIState()
    object EmptyState : BaseUIState()
    class ErrorState(val message: String) : BaseUIState()
}