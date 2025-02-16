package uk.gov.onelogin.criorchestrator.features.session.internal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.testdouble.SystemLogger

class InMemorySessionStoreTest {
    private val fakeSystemLogger = SystemLogger()
    private val fakeSessionStore =
        InMemorySessionStore(
            fakeSystemLogger,
        )

    @Test
    fun `session store reads default value of false`() {
        val result = fakeSessionStore.read().value
        assertEquals(false, result)
    }

    @Test
    fun `session store can read the written value`() {
        fakeSessionStore.write(true)
        val result = fakeSessionStore.read().value
        assertEquals(true, result)
    }
}
