package uk.gov.onelogin.criorchestrator.features.config.internal

import app.cash.turbine.test
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl

class InMemoryConfigStoreTest {
    private val logger = SystemLogger()

    @Test
    fun `given empty config, when ask for missing value, it throws exception`() =
        runTest {
            val configStore =
                givenConfigStore(
                    initialConfig =
                        Config(
                            entries = persistentListOf(),
                        ),
                )
            configStore.read(IdCheckAsyncBackendBaseUrl).test {
                val error = awaitError()
                assertInstanceOf<NoSuchElementException>(error)
                assertEquals("key: IdCheckAsyncBackendBaseUrl", error.message)
            }
        }

    @Test
    fun `given config, when read value, it emits value`() =
        runTest {
            val configStore = givenConfigStore()
            configStore.read(IdCheckAsyncBackendBaseUrl).test {
                assertEquals(
                    StubConfig.ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE,
                    awaitItem().value,
                )
            }
        }

    @Test
    fun `given config, when write value, it emits new value`() =
        runTest {
            val configStore = givenConfigStore()
            configStore.read(IdCheckAsyncBackendBaseUrl).test {
                awaitItem()
                configStore.writeIdCheckAsyncBackendUrl(
                    "updated value",
                )
                assertEquals(
                    "updated value",
                    awaitItem().value,
                )
            }
        }

    @Test
    fun `given config, when write same value twice, it emits only once`() =
        runTest {
            val configStore = givenConfigStore()
            configStore.read(IdCheckAsyncBackendBaseUrl).test {
                awaitItem()

                // Write duplicate values
                configStore.writeIdCheckAsyncBackendUrl(StubConfig.ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE)
                configStore.writeIdCheckAsyncBackendUrl("updated value")
                configStore.writeIdCheckAsyncBackendUrl("updated value")
                configStore.writeIdCheckAsyncBackendUrl("updated value 2")
                configStore.writeIdCheckAsyncBackendUrl("updated value 2")

                // It only emits distinct new values
                assertEquals(
                    "updated value",
                    awaitItem().value,
                )
                assertEquals(
                    "updated value 2",
                    awaitItem().value,
                )
            }
        }

    private fun ConfigStore.writeIdCheckAsyncBackendUrl(value: String) =
        write(
            Config.Entry(
                key = IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue(value),
            ),
        )

    private fun givenConfigStore(initialConfig: Config = StubConfig.config) =
        InMemoryConfigStore(
            logger,
            initialConfig = initialConfig,
        )
}
