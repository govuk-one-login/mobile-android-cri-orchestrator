package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nononchippedid

sealed class ConfirmNoNonChippedIDAction {
    data object NavigateToConfirmAbortMobile : ConfirmNoNonChippedIDAction()

    data object NavigateToConfirmAbortDesktop : ConfirmNoNonChippedIDAction()
}
