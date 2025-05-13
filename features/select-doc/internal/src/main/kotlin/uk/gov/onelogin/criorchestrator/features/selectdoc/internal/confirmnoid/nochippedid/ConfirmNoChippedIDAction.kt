package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

sealed class ConfirmNoChippedIDAction {
    data object NavigateToConfirmAbortMobile : ConfirmNoChippedIDAction()

    data object NavigateToConfirmAbortDesktop : ConfirmNoChippedIDAction()
}
