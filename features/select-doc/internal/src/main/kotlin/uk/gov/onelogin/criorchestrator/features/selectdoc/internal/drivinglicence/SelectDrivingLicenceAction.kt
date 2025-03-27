package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

sealed class SelectDrivingLicenceAction {
    data object NavigateToBrp : SelectDrivingLicenceAction()

    data object NavigateToAbort : SelectDrivingLicenceAction()

    data object NavigateToConfirmation : SelectDrivingLicenceAction()

    data object NavigateToTypesOfPhotoID : SelectDrivingLicenceAction()

    data object NavigateToHomeScreen : SelectDrivingLicenceAction()
}
