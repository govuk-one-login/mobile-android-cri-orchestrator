package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore

class IsSessionAbortedOrUnavailableImplTest {
    @Test
    fun `when active session is null, IsSessionAbortedOrUnavailableImpl returns true`() =
        runTest {
            val sessionStore = FakeSessionStore(null)
            val isSessionAbortedOrUnavailable = IsSessionAbortedOrUnavailableImpl(sessionStore)
            isSessionAbortedOrUnavailable().test {
                assertTrue(awaitItem())
            }
        }

    @Test
    fun `when active session is not null, IsSessionAbortedOrUnavailableImpl returns false`() =
        runTest {
            val sessionStore = FakeSessionStore()
            val isSessionAbortedOrUnavailable = IsSessionAbortedOrUnavailableImpl(sessionStore)
            isSessionAbortedOrUnavailable().test {
                assertFalse(awaitItem())
            }
        }
}
