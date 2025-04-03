package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.datastore.core.DataStoreFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import uk.gov.logging.api.Logger
import uk.gov.logging.testdouble.SystemLogger
import java.io.File

fun PersistedSessionStore.Companion.createTestInstance(
    testDispatcher: TestDispatcher,
    tempDir: File,
    logger: Logger = SystemLogger(),
): PersistedSessionStore {
    val coroutineScope = TestScope(testDispatcher + Job())
    val file = File(tempDir, "session-store.test.db")
    val dataStore =
        DataStoreFactory.create(
            scope = coroutineScope,
            produceFile = { file },
            serializer = SessionDataStoreSerializer(logger),
        )
    return PersistedSessionStore(logger, dataStore)
}
