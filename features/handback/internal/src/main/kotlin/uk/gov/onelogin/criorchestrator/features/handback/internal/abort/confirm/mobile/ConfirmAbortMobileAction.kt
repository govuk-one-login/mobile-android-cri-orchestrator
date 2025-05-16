package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

sealed class ConfirmAbortMobileAction {
    data class ContinueGovUk(
        val redirectUri: String,
    ) : ConfirmAbortMobileAction()
}
