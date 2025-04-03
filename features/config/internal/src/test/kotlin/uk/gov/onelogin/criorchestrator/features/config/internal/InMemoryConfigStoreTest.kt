package uk.gov.onelogin.criorchestrator.features.config.internal

import app.cash.turbine.test
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.StubStringConfigKey
import uk.gov.onelogin.criorchestrator.features.config.publicapi.stubConfig
import uk.gov.onelogin.criorchestrator.features.config.publicapi.stubStringConfigEntry

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
            configStore.read(StubStringConfigKey).test {
                val error = awaitError()
                assertInstanceOf<NoSuchElementException>(error)
                assertEquals("key: StubStringConfigKey", error.message)
            }
        }

    @Test
    fun `given config, when read value, it emits value`() =
        runTest {
            val configStore = givenConfigStore()
            configStore.read(StubStringConfigKey).test {
                assertEquals(
                    stubStringConfigEntry().value.value,
                    awaitItem().value,
                )
            }
        }

    @Test
    fun `given config, when read all values, it emits all values`() =
        runTest {
            val configStore = givenConfigStore()
            configStore.readAll().test {
                assertEquals(
                    stubConfig(),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given config, when write value, it emits new value`() =
        runTest {
            val configStore = givenConfigStore()
            configStore.read(StubStringConfigKey).test {
                awaitItem()
                configStore.writeStubStringConfig(
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
            configStore.read(StubStringConfigKey).test {
                awaitItem()

                // Write duplicate values
                configStore.writeStubStringConfig(stubStringConfigEntry().value.value)
                configStore.writeStubStringConfig("updated value")
                configStore.writeStubStringConfig("updated value")
                configStore.writeStubStringConfig("updated value 2")
                configStore.writeStubStringConfig("updated value 2")

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

    @Test
    fun `given config, when reading value synchronously, a value is returned`() {
        val configStore = givenConfigStore()
        assertEquals(
            "stub string config value",
            configStore.readSingle(StubStringConfigKey).value,
        )
    }

    private fun ConfigStore.writeStubStringConfig(value: String) =
        write(
            stubStringConfigEntry(
                value = value,
            ),
        )

    private fun givenConfigStore(initialConfig: Config = stubConfig()) =
        InMemoryConfigStore(
            logger,
            initialConfig = initialConfig,
        )
}
