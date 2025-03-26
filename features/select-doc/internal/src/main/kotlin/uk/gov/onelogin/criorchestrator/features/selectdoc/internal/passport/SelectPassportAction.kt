package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

sealed class SelectPassportAction {
    data object NavigateToBrp : SelectPassportAction()

    data object NavigateToConfirmation : SelectPassportAction()

    data object NavigateToTypesOfPhotoID : SelectPassportAction()
}
