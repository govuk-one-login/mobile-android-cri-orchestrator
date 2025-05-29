package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class IsSessionResumableImplTest {
    private val sessionStore = FakeSessionStore(session = null)
    private val isSessionResumable =
        IsSessionResumableImpl(
            sessionStore = sessionStore,
        )

    @Test
    fun `given no stored session, it returns false`() =
        runTest {
            isSessionResumable().test {
                assertFalse(awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `when session is created, it emits true`() =
        runTest {
            isSessionResumable().test {
                skipItems(1)

                sessionStore.write(Session.createTestInstance())
                assertTrue(awaitItem())
            }
        }

    @Test
    fun `when session becomes inactive, it emits false`() =
        runTest {
            isSessionResumable().test {
                skipItems(1)

                sessionStore.write(Session.createTestInstance())
                skipItems(1)

                sessionStore.updateToAborted()
                assertFalse(awaitItem())
            }
        }
}
