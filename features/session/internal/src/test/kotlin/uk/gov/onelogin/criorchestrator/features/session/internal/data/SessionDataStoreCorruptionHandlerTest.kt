package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

class SessionDataStoreCorruptionHandlerTest {
    @Test
    fun `it returns null`() =
        runTest {
            val result =
                sessionDataStoreCorruptionHandler.handleCorruption(
                    CorruptionException("test"),
                )

            assertNull(result)
        }
}
