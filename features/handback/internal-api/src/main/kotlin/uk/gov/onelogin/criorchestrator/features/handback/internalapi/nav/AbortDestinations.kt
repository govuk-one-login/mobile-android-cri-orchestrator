package uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface AbortDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object ConfirmAbortDesktop : AbortDestinations

    @Serializable
    data object ConfirmAbortMobile : AbortDestinations

    @Serializable
    data object AbortedReturnToDesktopWeb : AbortDestinations

    @Serializable
    data class AbortedRedirectToMobileWebHolder(
        val redirectUri: String,
    ) : AbortDestinations
}
