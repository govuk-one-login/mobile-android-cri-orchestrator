package uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.EncodeDefault.Mode
import kotlinx.serialization.ExperimentalSerializationApi
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

    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class ConfirmAbortDesktop(
        @EncodeDefault(Mode.ALWAYS)
        val finishJourney: Boolean = false,
    ) : HandbackDestinations

    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class ConfirmAbortMobile(
        @EncodeDefault(Mode.ALWAYS)
        val finishJourney: Boolean = false,
    ) : HandbackDestinations
}
