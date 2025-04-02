package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

sealed class ConfirmPassportAction {
    data object NavigateToSyncIdCheck : ConfirmPassportAction() {
        val documentVariety: DocumentVariety = DocumentVariety.NFC_PASSPORT
    }
}
