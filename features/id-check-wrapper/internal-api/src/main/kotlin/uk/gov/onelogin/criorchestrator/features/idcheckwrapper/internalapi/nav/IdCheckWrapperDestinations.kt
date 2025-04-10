package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface IdCheckWrapperDestinations : ProveYourIdentityDestinations {
    @Serializable
    data class SyncIdCheckScreen(
        val documentVariety: DocumentVariety,
    ) : IdCheckWrapperDestinations
}
