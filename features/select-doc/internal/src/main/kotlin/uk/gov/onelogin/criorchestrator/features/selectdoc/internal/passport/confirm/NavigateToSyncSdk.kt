package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

data object NavigateToSyncSdk {
    val documentVariety: DocumentVariety = DocumentVariety.NFC_PASSPORT
}
