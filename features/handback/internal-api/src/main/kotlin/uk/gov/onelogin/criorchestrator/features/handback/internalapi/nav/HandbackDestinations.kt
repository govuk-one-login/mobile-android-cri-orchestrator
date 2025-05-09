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

    // This should be removed with DCMAW-13211
    @Serializable
    data object ConfirmAbort : HandbackDestinations

    @Serializable
    data object ConfirmAbortDesktop : HandbackDestinations

    @Serializable
    data object ConfirmAbortMobile : HandbackDestinations
}
