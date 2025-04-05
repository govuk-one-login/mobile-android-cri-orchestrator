package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm

import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

sealed class ConfirmBrpAction {
    data object NavigateToSyncIdCheck : ConfirmBrpAction() {
        val documentVariety: DocumentVariety = DocumentVariety.BRP
    }
}
