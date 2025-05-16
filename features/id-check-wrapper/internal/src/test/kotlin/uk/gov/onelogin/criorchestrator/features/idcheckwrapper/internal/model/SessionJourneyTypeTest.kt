package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.journeyType

class SessionJourneyTypeTest {
    @Test
    fun `given it has redirect URI, journey type is mobile-app-mobile (MAM)`() {
        val session =
            Session.Companion.createTestInstance(
                redirectUri = "https://example.com",
            )

        assertEquals(session.journeyType, JourneyType.MobileAppMobile("https://example.com"))
    }

    @Test
    fun `given it has a blank redirect URI, journey type is mobile-app-mobile (MAM)`() {
        val session =
            Session.Companion.createTestInstance(
                redirectUri = " ",
            )

        assertEquals(session.journeyType, JourneyType.MobileAppMobile(" "))
    }

    @Test
    fun `given it has a zero-length redirect URI, journey type is desktop-app-desktop (DAD)`() {
        val session =
            Session.Companion.createTestInstance(
                redirectUri = "",
            )

        assertEquals(session.journeyType, JourneyType.DesktopAppDesktop)
    }

    @Test
    fun `given it doesn't have a redirect URI, journey type is desktop-app-desktop (DAD)`() {
        val session =
            Session.Companion.createTestInstance(
                redirectUri = null,
            )

        assertEquals(session.journeyType, JourneyType.DesktopAppDesktop)
    }
}
