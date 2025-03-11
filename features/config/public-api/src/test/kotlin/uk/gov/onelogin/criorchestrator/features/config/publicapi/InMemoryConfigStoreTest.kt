package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.gov.logging.testdouble.SystemLogger

class InMemoryConfigStoreTest {
    private val logger = SystemLogger()
    private val configStore = InMemoryConfigStore(logger)

    @Test
    fun `empty config store throws exception when trying to get value of non-existent key`() {
        assertThrows<NoSuchElementException> {
            configStore.read(ConfigField.BackendAsyncUrl)
        }
    }

    @Test
    fun `write map to config store and read successfully from config store`() {
        runTest {
            configStore.write(StubConfig.provideConfig())
            val configStoreReadResult = configStore.read(ConfigField.BackendAsyncUrl)
            assertEquals(StubConfig.BACKEND_ASYNC_URL_TEST_VALUE, configStoreReadResult.value)
        }
    }
}
