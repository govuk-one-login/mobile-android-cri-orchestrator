package uk.gov.onelogin.criorchestrator.features.handback.internal.unabletoconfirmidentity.mobile

sealed interface UnableToConfirmIdentityMobileAction {
    data class ContinueGovUk(
        val redirectUri: String,
    ) : UnableToConfirmIdentityMobileAction

    data object NavigateToUnrecoverableError : UnableToConfirmIdentityMobileAction

    data object NavigateToOfflineError : UnableToConfirmIdentityMobileAction
}
