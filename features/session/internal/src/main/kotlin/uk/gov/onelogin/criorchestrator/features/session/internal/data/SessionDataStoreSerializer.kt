package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class SessionDataStoreSerializer
    @Inject
    constructor(
        private val logger: Logger,
    ) : Serializer<Session?>,
        LogTagProvider {
        override val defaultValue = null

        override suspend fun readFrom(input: InputStream): Session? =
            try {
                Json
                    .decodeFromString<SessionRecord>(
                        input.readBytes().decodeToString(),
                    ).session
            } catch (serialization: SerializationException) {
                val exception = CorruptionException("Unable to restore Session", serialization)
                logger.error(tag, "Error restoring session", exception)
                throw exception
            }

        override suspend fun writeTo(
            session: Session?,
            output: OutputStream,
        ) {
            output.write(
                Json
                    .encodeToString(SessionRecord(session))
                    .encodeToByteArray(),
            )
        }
    }

@Serializable
internal data class SessionRecord(
    val session: Session?,
)
