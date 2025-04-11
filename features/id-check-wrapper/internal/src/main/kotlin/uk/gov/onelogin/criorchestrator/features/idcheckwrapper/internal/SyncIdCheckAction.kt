package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

sealed class SyncIdCheckAction {
    data object NavigateToRecoverableError : SyncIdCheckAction()

    data object NavigateToUnRecoverableError : SyncIdCheckAction()
}
