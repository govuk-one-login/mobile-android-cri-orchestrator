package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

enum class JourneyType {
    MobileAppMobile,
    DesktopAppDesktop,
}

val Session.journeyType: JourneyType
    get() =
        when (this.redirectUri.isNullOrEmpty()) {
            true -> JourneyType.DesktopAppDesktop
            false -> JourneyType.MobileAppMobile
        }
