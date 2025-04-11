package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

sealed class SyncIdCheckAction {
    data object NavigateToRecoverableError : SyncIdCheckAction()

    data object NavigateToUnRecoverableError : SyncIdCheckAction()
}