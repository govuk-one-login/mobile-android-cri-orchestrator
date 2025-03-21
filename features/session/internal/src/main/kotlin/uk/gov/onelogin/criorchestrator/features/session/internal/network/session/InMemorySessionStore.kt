package uk.gov.onelogin.criorchestrator.features.session.internal.network.session

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class, boundType = SessionStore::class)
class InMemorySessionStore
    @Inject
    constructor(
        private val logger: Logger,
    ) : SessionStore,
        LogTagProvider {
        private var activeSession: MutableStateFlow<Session?> = MutableStateFlow(null)

        override fun read(): StateFlow<Session?> {
            logger.debug(tag, "Reading session ${activeSession.value} from session store")
            return activeSession.asStateFlow()
        }

        override fun write(value: Session?) {
            logger.debug(tag, "Writing $value to session store")
            activeSession.value = value
        }
    }
