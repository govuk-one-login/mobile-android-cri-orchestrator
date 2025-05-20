package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm

sealed interface ConfirmAbortState {
    data object Loading : ConfirmAbortState

    data object Display : ConfirmAbortState
}
