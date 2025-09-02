package uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface HandbackDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object UnrecoverableError : HandbackDestinations

    @Serializable
    data class ReturnToMobileWeb(
        val redirectUri: String,
    ) : HandbackDestinations

    @Serializable
    data object ReturnToDesktopWeb : HandbackDestinations

    @Serializable
    data class FaceScanLimitReachedMobile(
        val redirectUri: String,
    ) : HandbackDestinations
}
