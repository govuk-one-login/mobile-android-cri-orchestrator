package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation

sealed class ConfirmDocumentAction {
    data object NavigateToDocumentPhotoScanner: ConfirmDocumentAction()
}