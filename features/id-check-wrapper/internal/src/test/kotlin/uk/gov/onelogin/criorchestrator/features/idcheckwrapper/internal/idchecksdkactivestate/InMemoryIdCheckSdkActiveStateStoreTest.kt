package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.logging.testdouble.SystemLogger

class InMemoryIdCheckSdkActiveStateStoreTest {
    private val logger = SystemLogger()
    private val inMemoryIdCheckSdkActiveStateStore = InMemoryIdCheckSdkActiveStateStore(logger)

    @Test
    fun `active state store returns false by default`() {
        assertFalse(inMemoryIdCheckSdkActiveStateStore.read().value)
        assertTrue(logger.contains("Reading state false from ID Check SDK active state store"))
    }

    @Test
    fun `active state store returns true after being set true`() {
        inMemoryIdCheckSdkActiveStateStore.setActive()
        assertTrue(logger.contains("Writing state true to ID Check SDK active state store"))
        assertTrue(inMemoryIdCheckSdkActiveStateStore.read().value)
        assertTrue(logger.contains("Reading state true from ID Check SDK active state store"))
    }

    @Test
    fun `active state store returns false after being set false`() {
        inMemoryIdCheckSdkActiveStateStore.setActive()
        assertTrue(inMemoryIdCheckSdkActiveStateStore.read().value)
        assertTrue(logger.contains("Writing state true to ID Check SDK active state store"))
        assertTrue(logger.contains("Reading state true from ID Check SDK active state store"))
        assertTrue(logger.size == 2)
        inMemoryIdCheckSdkActiveStateStore.setInactive()
        assertFalse(inMemoryIdCheckSdkActiveStateStore.read().value)
        assertTrue(logger.contains("Writing state false to ID Check SDK active state store"))
        assertTrue(logger.contains("Reading state false from ID Check SDK active state store"))
        assertTrue(logger.size == 4)
    }
}
