package uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface HandbackDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object UnrecoverableError : HandbackDestinations

    @Serializable
    data object Abort : HandbackDestinations

    @Serializable
    data object ReturnToMobileWeb : HandbackDestinations

    @Serializable
    data object ReturnToDesktopWeb : HandbackDestinations
}
