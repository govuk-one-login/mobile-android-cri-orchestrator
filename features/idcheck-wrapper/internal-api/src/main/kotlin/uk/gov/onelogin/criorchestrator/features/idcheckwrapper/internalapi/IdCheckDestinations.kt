package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import kotlinx.serialization.Serializable
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface IdCheckDestinations : ProveYourIdentityDestinations {
    @Serializable
    data class SyncIdCheck(
        val idCheckSdkParameters: IdCheckSdkParameters
    ) : IdCheckDestinations
}
