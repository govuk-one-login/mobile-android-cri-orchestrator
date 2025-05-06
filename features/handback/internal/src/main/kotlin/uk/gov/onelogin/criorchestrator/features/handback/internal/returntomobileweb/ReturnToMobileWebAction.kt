package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

sealed class ReturnToMobileWebAction {
    data class ContinueToGovUk(
        val redirectUri: String,
    ) : ReturnToMobileWebAction()
}
