package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.navigation

import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.IdCheckWrapperDestinations

fun  IdCheckWrapperDestinations.SyncIdCheckScreen.toDocumentType(): DocumentType =
    when (documentVariety) {
        DocumentVariety.NFC_PASSPORT -> DocumentType.NFC_PASSPORT
        DocumentVariety.BRP -> DocumentType.BRP
        DocumentVariety.DRIVING_LICENCE -> DocumentType.DRIVING_LICENCE
    }
