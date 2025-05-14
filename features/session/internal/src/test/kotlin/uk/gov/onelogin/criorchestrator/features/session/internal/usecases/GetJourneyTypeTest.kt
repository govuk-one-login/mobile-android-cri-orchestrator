package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
                    session = Session.Companion.createMobileAppMobileInstance(),
                )
            val getJourneyType = GetJourneyTypeImpl(sessionStore)

            assertEquals(JourneyType.MobileAppMobile, getJourneyType())
        }

    @Test
    fun `when active session does not have redirect uri, it returns DAD journey`() =
        runTest {
            val sessionStore =
                FakeSessionStore(
                    session = Session.Companion.createDesktopAppDesktopInstance(),
                )
            val getJourneyType = GetJourneyTypeImpl(sessionStore)

            assertEquals(JourneyType.DesktopAppDesktop, getJourneyType())
        }

    @Test
    fun `when active session is null, expected crash occurs`() =
        runTest {
            val sessionStore =
                FakeSessionStore(
                    session = null,
                )

            val exception =
                assertThrows<IllegalStateException> {
                    GetJourneyTypeImpl(sessionStore).invoke()
                }
            assertEquals("Session is not set", exception.message)
        }
}
