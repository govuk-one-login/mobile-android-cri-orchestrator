package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

sealed class ReturnToMobileWebAction {
    object ContinueToGovUk : ReturnToMobileWebAction()
}
