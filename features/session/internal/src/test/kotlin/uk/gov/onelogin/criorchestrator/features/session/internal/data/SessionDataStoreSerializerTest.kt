package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.createTestInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class SessionDataStoreSerializerTest {
    private val logger = SystemLogger()
    private val serializer =
        SessionDataStoreSerializer(
            logger = logger,
        )

    private companion object {
        val session = Session.createTestInstance()

        val nullSession: Session? = null

        const val INVALID_SESSION_JSON = """{session:{"invalid":"json"}}"""
    }

    @Test
    fun `serialize then deserialize session`() =
        runTest {
            val outputStream = ByteArrayOutputStream()

            serializer.writeTo(session, outputStream)
            val written = outputStream.toByteArray()

            val restored = serializer.readFrom(written.inputStream())

            assertEquals(session, restored)
        }

    @Test
    fun `serialize then deserialize null session`() =
        runTest {
            val outputStream = ByteArrayOutputStream()

            serializer.writeTo(nullSession, outputStream)
            val written = outputStream.toByteArray()

            val restored = serializer.readFrom(written.inputStream())

            assertEquals(nullSession, restored)
        }

    @Test
    fun `given invalid session, it throws`() =
        runTest {
            val input = INVALID_SESSION_JSON.toByteArray()

            val exception =
                assertThrows<CorruptionException> {
                    serializer.readFrom(input.inputStream())
                }

            assertEquals("Unable to restore Session", exception.message)
        }

    @Test
    fun `given invalid session, it logs`() =
        runTest {
            val input = INVALID_SESSION_JSON.toByteArray()

            assertThrows<CorruptionException> {
                serializer.readFrom(input.inputStream())
            }

            assertTrue(logger.contains("Error restoring session"))
        }
}
