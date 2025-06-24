package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import uk.gov.logging.testdouble.SystemLogger

class IsIdCheckSdkActiveImplTest {
    private val logger = SystemLogger()
    private val idCheckSdkActiveStateStore = InMemoryIdCheckSdkActiveStateStore(logger)
    private val isIdCheckSdkActiveImpl = IsIdCheckSdkActiveImpl(idCheckSdkActiveStateStore)

    @Test
    fun `given ID Check SDK is inactive, then isIdCheckSdkActiveImpl returns false`() {
        idCheckSdkActiveStateStore.setInactive()
        assertFalse(isIdCheckSdkActiveImpl())
    }

    @Test
    fun `given ID Check SDK is active, then isIdCheckSdkActiveImpl returns true`() {
        idCheckSdkActiveStateStore.setActive()
        assertTrue(isIdCheckSdkActiveImpl())
    }
}
