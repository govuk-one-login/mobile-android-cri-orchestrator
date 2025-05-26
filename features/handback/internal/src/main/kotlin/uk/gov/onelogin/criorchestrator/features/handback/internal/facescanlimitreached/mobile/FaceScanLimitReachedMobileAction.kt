package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.mobile

sealed interface FaceScanLimitReachedMobileAction {
    data class ContinueGovUk(
        val redirectUri: String,
    ) : FaceScanLimitReachedMobileAction

    data object NavigateToUnrecoverableError : FaceScanLimitReachedMobileAction

    data object NavigateToOfflineError : FaceScanLimitReachedMobileAction
}
