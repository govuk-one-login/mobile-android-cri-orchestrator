package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

sealed class SelectDrivingLicenceAction {
    data object NavigateToConfirmation : SelectDrivingLicenceAction()

    data object NavigateToConfirmNoChippedID : SelectDrivingLicenceAction()

    data object NavigateToConfirmNoNonChippedID : SelectDrivingLicenceAction()

    data object NavigateToTypesOfPhotoID : SelectDrivingLicenceAction()
}
