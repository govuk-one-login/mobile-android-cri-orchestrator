package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

sealed class ConfirmNoChippedIDAction {
    data object NavigateToConfirmAbort : ConfirmNoChippedIDAction()
}