package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

sealed class SelectBrpAction {
    data object NavigateToBrpConfirmation : SelectBrpAction()

    data object NavigateToDrivingLicence : SelectBrpAction()

    data object NavigateToTypesOfPhotoID : SelectBrpAction()
}
