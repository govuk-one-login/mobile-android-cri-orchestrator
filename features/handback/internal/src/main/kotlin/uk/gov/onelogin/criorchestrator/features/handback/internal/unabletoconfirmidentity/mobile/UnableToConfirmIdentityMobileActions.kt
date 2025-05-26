package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile

sealed interface UnableToConfirmIdentityMobileActions {
    data class ContinueGovUk(
        val redirectUri: String,
    ) : UnableToConfirmIdentityMobileActions

    data object NavigateToUnrecoverableError : UnableToConfirmIdentityMobileActions

    data object NavigateToOfflineError : UnableToConfirmIdentityMobileActions
}
