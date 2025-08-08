package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface JourneyType {
    @Parcelize
    data class MobileAppMobile(
        val redirectUri: String,
    ) : JourneyType,
        Parcelable

    @Parcelize
    data object DesktopAppDesktop : JourneyType, Parcelable
}

val Session.journeyType: JourneyType
    get() =
        when (this.redirectUri.isNullOrEmpty()) {
            true -> JourneyType.DesktopAppDesktop
            false -> JourneyType.MobileAppMobile(redirectUri = this.redirectUri)
        }
