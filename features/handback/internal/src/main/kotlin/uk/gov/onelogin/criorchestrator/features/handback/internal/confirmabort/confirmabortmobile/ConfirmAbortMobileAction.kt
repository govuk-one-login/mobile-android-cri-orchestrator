package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortmobile

sealed class ConfirmAbortMobileAction {
    data class ContinueGovUk(
        val redirectUri: String,
    ) : ConfirmAbortMobileAction()
}
