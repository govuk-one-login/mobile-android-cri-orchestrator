package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.datastore.core.DataStore
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@ContributesBinding(CriOrchestratorSingletonScope::class, boundType = SessionStore::class)
class PersistedSessionStore
    @Inject
    constructor(
        private val logger: Logger,
        @Named(SessionStoreModule.SESSION_STORE_DATASTORE_ID)
        private val dataStore: DataStore<Session?>,
    ) : SessionStore,
        LogTagProvider {
        companion object;

        override fun read(): Flow<Session?> = dataStore.data

        override suspend fun write(value: Session?) {
            logger.debug(tag, "Writing $value to session store")
            dataStore.updateData { value }
        }
    }
