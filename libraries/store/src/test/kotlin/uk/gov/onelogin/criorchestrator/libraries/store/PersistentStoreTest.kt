package uk.gov.onelogin.criorchestrator.libraries.store

import app.cash.turbine.test
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.libraries.store.securestore.FakeSecureStoreAsyncV2

class PersistentStoreTest {
    companion object {
        private const val KEY = "test_key"
    }

    private val newValue = "new_value"
    private val testScope = TestScope()
    private val secureStore = FakeSecureStoreAsyncV2()
    private val persistentStore = createStore()

    private fun createStore() =
        PersistentStore(
            secureStore = secureStore,
            key = KEY,
            logger = SystemLogger(),
            coroutineScope = testScope,
            serializer = String.serializer(),
        )

    @Test
    fun `given no data, read returns null`() =
        testScope.runTest {
            persistentStore.read().test {
                assertEquals(null, awaitItem())
            }
        }

    @Test
    fun `given stored session, new store instance reads the stored value`() =
        testScope.runTest {
            persistentStore.write(newValue)

            val newStore = createStore()
            assertEquals(newValue, newStore.read().value)
        }

    @Test
    fun `given secure store has data but throws on load, it deletes all data and returns null`() =
        testScope.runTest {
            persistentStore.write(newValue)
            assertTrue(secureStore.hasData())

            secureStore.throwOnRetrieve = true
            val store = createStore()

            assertNull(store.read().value)
            assertFalse(secureStore.hasData())
        }

    @Test
    fun `given secure store throws on upsert, when write called, value is not updated`() =
        testScope.runTest {
            secureStore.throwOnUpsert = true

            persistentStore.write(newValue)

            assertNull(persistentStore.read().value)
        }

    @Test
    fun `given secure store throws on delete, when clear called, value is not updated`() =
        testScope.runTest {
            persistentStore.write(newValue)
            secureStore.throwOnDelete = true

            persistentStore.clear()

            assertEquals(newValue, persistentStore.read().value)
        }

    @Test
    fun `given secure store has data but throws on load and deleteAll also fails, returns null`() =
        testScope.runTest {
            persistentStore.write(newValue)
            assertTrue(secureStore.hasData())

            secureStore.throwOnRetrieve = true
            secureStore.throwOnDeleteAll = true
            val store = createStore()

            assertNull(store.read().value)
        }
}
