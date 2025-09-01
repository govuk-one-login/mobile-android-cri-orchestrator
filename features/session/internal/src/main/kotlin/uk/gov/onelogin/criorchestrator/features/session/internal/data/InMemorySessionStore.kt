package uk.gov.onelogin.criorchestrator.features.session.internal.data

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@SingleIn(CriOrchestratorSingletonScope::class)
@ContributesBinding(CriOrchestratorSingletonScope::class, binding = binding<SessionStore>())
class InMemorySessionStore
    @Inject
    constructor(
        private val logger: Logger,
    ) : SessionStore,
        LogTagProvider {
        private var session: MutableStateFlow<Session?> = MutableStateFlow(null)

        override fun read(): StateFlow<Session?> {
            logger.debug(tag, "Reading session ${session.value} from session store")
            return session.asStateFlow()
        }

        override fun write(value: Session) {
            logger.debug(tag, "Writing $value to session store")
            session.value = value
        }

        override fun clear() {
            logger.debug(tag, "Clearing the session store")
            session.value = null
        }

        override fun updateToAborted() =
            with(session) {
                value = value?.copyUpdateState { advanceAtLeastAborted() }
            }

        override fun updateToDocumentSelected() =
            with(session) {
                value = value?.copyUpdateState { advanceAtLeastDocumentSelected() }
            }
    }
