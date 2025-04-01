package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import kotlinx.serialization.Serializable
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface IdCheckWrapperDestinations : ProveYourIdentityDestinations {
    @Serializable
    data class SyncIdCheckScreen(
        val documentType: DocumentType,
    ) : IdCheckWrapperDestinations
}
