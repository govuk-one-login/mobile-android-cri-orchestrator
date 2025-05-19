package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm

sealed interface ConfirmAbortDisplayState {
    data object Loading : ConfirmAbortDisplayState

    data object Display : ConfirmAbortDisplayState
}