package uk.gov.onelogin.criorchestrator.features.error.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface ErrorDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object RecoverableError : ErrorDestinations
}
