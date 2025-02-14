package uk.gov.onelogin.criorchestrator.features.session.internal

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class InMemorySessionStore
@Inject
constructor(
    private val logger: Logger,
) : SessionStore {
    private var isActiveSession: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun read(): StateFlow<Boolean> = isActiveSession.asStateFlow()

    override fun write(value: Boolean) {
        logger.info(
            tag = tag,
            msg = "Writing $value to active session store",
        )
        isActiveSession.value = value
    }

    companion object {
        private val tag = this.javaClass.simpleName
    }
}
