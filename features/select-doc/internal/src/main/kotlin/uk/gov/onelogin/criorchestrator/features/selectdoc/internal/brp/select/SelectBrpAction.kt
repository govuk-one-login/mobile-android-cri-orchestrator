package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select

sealed class SelectBrpAction {
    data object NavigateToBrpConfirmation : SelectBrpAction()

    data object NavigateToDrivingLicence : SelectBrpAction()

    data object NavigateToTypesOfPhotoID : SelectBrpAction()
}
