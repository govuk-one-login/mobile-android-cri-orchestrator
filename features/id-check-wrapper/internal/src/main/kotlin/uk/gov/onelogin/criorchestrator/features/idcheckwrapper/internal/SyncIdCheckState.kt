package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

sealed interface SyncIdCheckState {
    object Loading : SyncIdCheckState

    object Idle : SyncIdCheckState
}
