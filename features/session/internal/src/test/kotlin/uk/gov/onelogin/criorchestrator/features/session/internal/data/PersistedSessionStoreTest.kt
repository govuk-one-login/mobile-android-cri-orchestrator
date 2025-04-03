package uk.gov.onelogin.criorchestrator.features.session.internal.data

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import uk.gov.onelogin.criorchestrator.features.session.internalapi.createTestInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import java.io.File

class PersistedSessionStoreTest {
    @field:TempDir
    lateinit var tempDir: File

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val sessionStore by lazy {
        PersistedSessionStore.createTestInstance(
            testDispatcher = testDispatcher,
            tempDir = tempDir,
        )
    }
    private val session = Session.createTestInstance()

    @Test
    fun `session store returns null session if no session written`() =
        runTest(
            testDispatcher.scheduler,
        ) {
            sessionStore
                .read()
                .test {
                    assertEquals(null, awaitItem())
                }
        }

    @Test
    fun `session store returns previously written session`() =
        runTest(
            testDispatcher.scheduler,
        ) {
            sessionStore.write(session)
            sessionStore.read().test {
                assertEquals(session, awaitItem())
            }
        }

    @Test
    fun `when write null, session store is cleared`() =
        runTest(
            testDispatcher.scheduler,
        ) {
            sessionStore.read().test {
                assertEquals(null, awaitItem())
                sessionStore.write(session)
                assertEquals(session, awaitItem())
                sessionStore.write(null)
                assertEquals(null, awaitItem())
            }
        }
}
