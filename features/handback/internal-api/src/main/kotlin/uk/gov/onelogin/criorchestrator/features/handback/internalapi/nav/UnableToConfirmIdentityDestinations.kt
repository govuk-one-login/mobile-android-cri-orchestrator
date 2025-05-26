package uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface UnableToConfirmIdentityDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object UnableToConfirmIdentityDesktop : UnableToConfirmIdentityDestinations

    @Serializable
    data object UnableToConfirmIdentityMobile : UnableToConfirmIdentityDestinations
}
