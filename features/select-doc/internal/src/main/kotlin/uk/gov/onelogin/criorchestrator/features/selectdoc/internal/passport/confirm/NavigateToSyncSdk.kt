package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType

data object NavigateToSyncSdk {
    val documentType: DocumentType = DocumentType.NFC_PASSPORT
}
