package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.confirm

import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

sealed class ConfirmDrivingLicenceAction {
    data object NavigateToSyncIdCheck : ConfirmDrivingLicenceAction() {
        val documentVariety: DocumentVariety = DocumentVariety.DRIVING_LICENCE
    }
}
