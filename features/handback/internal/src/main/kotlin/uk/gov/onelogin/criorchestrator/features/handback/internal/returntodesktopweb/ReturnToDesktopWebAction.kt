package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

sealed class ReturnToDesktopWebAction {
    data object RequestReview : ReturnToDesktopWebAction()
}
