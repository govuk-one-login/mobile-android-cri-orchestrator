package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav

import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

fun DocumentVariety.toDocumentType(): DocumentType =
    when (this) {
        DocumentVariety.NFC_PASSPORT -> DocumentType.NFC_PASSPORT
        DocumentVariety.BRP -> DocumentType.BRP
        DocumentVariety.DRIVING_LICENCE -> DocumentType.DRIVING_LICENCE
    }
