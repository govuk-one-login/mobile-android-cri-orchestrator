package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

sealed interface JourneyType {
    data class MobileAppMobile(
        val redirectUri: String,
    ) : JourneyType

    data object DesktopAppDesktop : JourneyType
}

val Session.journeyType: JourneyType
    get() =
        when (this.redirectUri.isNullOrEmpty()) {
            true -> JourneyType.DesktopAppDesktop
            false -> JourneyType.MobileAppMobile(redirectUri = this.redirectUri)
        }
