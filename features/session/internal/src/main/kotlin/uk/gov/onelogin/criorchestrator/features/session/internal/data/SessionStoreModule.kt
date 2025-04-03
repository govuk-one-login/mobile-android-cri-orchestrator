package uk.gov.onelogin.criorchestrator.features.session.internal.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@ContributesTo(CriOrchestratorSingletonScope::class)
class SessionStoreModule {
    /**
     * Never create more than one instance of DataStore for a given file in the same process.
     * Doing so can break all DataStore functionality. If there are multiple DataStores active
     * for a given file in the same process, DataStore will throw IllegalStateException when
     * reading or updating data.
     *
     * https://developer.android.com/topic/libraries/architecture/datastore#correct_usage
     */
    @Provides
    @Singleton
    @Named(SESSION_STORE_DATASTORE_ID)
    fun provideSessionDataStore(
        context: Context,
        serializer: SessionDataStoreSerializer,
        coroutineDispatchers: CoroutineDispatchers,
    ): DataStore<Session?> =
        DataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(SESSION_STORE_DATASTORE_ID)
            },
            corruptionHandler = sessionDataStoreCorruptionHandler,
            serializer = serializer,
            scope = CoroutineScope(coroutineDispatchers.io + SupervisorJob()),
        )

    companion object {
        // Changing this ID will change the name of the DataStore file, affecting data.
        const val SESSION_STORE_DATASTORE_ID =
            "uk.gov.onelogin.criorchestrator.features.session.data.idchecksession.v1"
    }
}
