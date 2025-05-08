package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmaborttomobileweb

sealed class ConfirmAbortToMobileWebAction {
    data class ContinueToGovUk(
        val redirectUri: String,
    ) : ConfirmAbortToMobileWebAction()
}
