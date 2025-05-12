package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.JourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createMobileAppMobileInstance
import kotlin.test.assertEquals

class GetJourneyTypeTest {
    @Test
    fun `when active session has redirect uri, it returns MAM journey`() =
        runTest {
            val sessionStore =
                FakeSessionStore(
                    session = Session.createMobileAppMobileInstance(),
                )
            val getJourneyType = GetJourneyTypeImpl(sessionStore)

            assertEquals(JourneyType.MobileAppMobile, getJourneyType())
        }

    @Test
    fun `when active session does not have redirect uri, it returns DAD journey`() =
        runTest {
            val sessionStore =
                FakeSessionStore(
                    session = Session.createDesktopAppDesktopInstance(),
                )
            val getJourneyType = GetJourneyTypeImpl(sessionStore)

            assertEquals(JourneyType.DesktopAppDesktop, getJourneyType())
        }
}
