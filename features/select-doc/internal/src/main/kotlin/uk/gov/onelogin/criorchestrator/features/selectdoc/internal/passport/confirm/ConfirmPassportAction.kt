package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

sealed class ConfirmPassportAction {
    data object NavigateToPassportPhotoScanner: ConfirmPassportAction()
}