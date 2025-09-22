package uk.gov.onelogin.criorchestrator.libraries.kotlinutils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoroutineDispatchersTest {
    // For some reason this doesn't play nicely when set up as a parameterized test.
    @Test
    fun `CoroutineDispatchers correctly provides IO dispatcher by default`() {
        assertEquals(
            Dispatchers.IO,
            CoroutineDispatchers().io,
        )
    }

    @Test
    fun `CoroutineDispatchers correctly provides Main dispatcher by default`() {
        assertEquals(
            Dispatchers.Default,
            CoroutineDispatchers().default,
        )
    }

    @Test
    fun `CoroutineDispatchers correctly provides Default dispatcher by default`() {
        assertEquals(
            Dispatchers.Main,
            CoroutineDispatchers().main,
        )
    }

    @Test
    fun `CoroutineDispatchers correctly provides Unconfined dispatcher by default`() {
        assertEquals(
            Dispatchers.Unconfined,
            CoroutineDispatchers().unconfined,
        )
    }

    @Test
    fun `CoroutineDispatchers io correctly provides Unconfined dispatcher when overwritten`() {
        val unconfinedCoroutineDispatchers = CoroutineDispatchers.from(Dispatchers.Unconfined)
        assertEquals(
            Dispatchers.Unconfined,
            unconfinedCoroutineDispatchers.io,
        )
    }

    @Test
    fun `CoroutineDispatchers default correctly provides Unconfined dispatcher when overwritten`() {
        val unconfinedCoroutineDispatchers = CoroutineDispatchers.from(Dispatchers.Unconfined)
        assertEquals(
            Dispatchers.Unconfined,
            unconfinedCoroutineDispatchers.default,
        )
    }

    @Test
    fun `CoroutineDispatchers main correctly provides Unconfined dispatcher when overwritten`() {
        val unconfinedCoroutineDispatchers = CoroutineDispatchers.from(Dispatchers.Unconfined)
        assertEquals(
            Dispatchers.Unconfined,
            unconfinedCoroutineDispatchers.main,
        )
    }

    @Test
    fun `CoroutineDispatchers unconfined correctly provides Main dispatcher when overwritten`() {
        val mainCoroutineDispatchers = CoroutineDispatchers.from(Dispatchers.Main)
        assertEquals(
            Dispatchers.Main,
            mainCoroutineDispatchers.unconfined,
        )
    }

    @Test
    fun `given testDispatcher is provided, defaultUnlessTest provides test dispatchers and main dispatcher`() {
        val testDispatcher = StandardTestDispatcher()

        val coroutineDispatchers =
            CoroutineDispatchers.defaultUnlessTest(
                testDispatcher = testDispatcher,
            )

        assertEquals(
            CoroutineDispatchers(
                default = testDispatcher,
                io = testDispatcher,
                main = Dispatchers.Main,
                unconfined = testDispatcher,
            ),
            coroutineDispatchers,
        )
    }

    @Test
    fun `given testDispatcher is null, defaultUnlessTest provides default dispatchers`() {
        val coroutineDispatchers =
            CoroutineDispatchers.defaultUnlessTest(
                testDispatcher = null,
            )

        assertEquals(
            CoroutineDispatchers(
                default = Dispatchers.Default,
                io = Dispatchers.IO,
                main = Dispatchers.Main,
                unconfined = Dispatchers.Unconfined,
            ),
            coroutineDispatchers,
        )
    }

    @Test
    fun `given testDispatcher is null and default is custom, defaultUnlessTest provides the custom dispatchers`() {
        val custom = CoroutineDispatchers.from(Dispatchers.Unconfined)
        val coroutineDispatchers =
            CoroutineDispatchers.defaultUnlessTest(
                testDispatcher = null,
                defaultDispatchers = custom,
            )

        assertEquals(
            custom,
            coroutineDispatchers,
        )
    }
}
