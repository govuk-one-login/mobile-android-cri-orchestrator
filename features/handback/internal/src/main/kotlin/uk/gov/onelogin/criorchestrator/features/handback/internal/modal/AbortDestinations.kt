package uk.gov.onelogin.criorchestrator.features.handback.internal.modal

import kotlinx.serialization.Serializable

sealed interface AbortDestinations {
    @Serializable
    data object ConfirmAbortDesktopWeb: AbortDestinations

    @Serializable
    data object ConfirmAbortReturnDesktopWeb: AbortDestinations

    @Serializable
    data object ConfirmAbortToMobileWeb: AbortDestinations

}