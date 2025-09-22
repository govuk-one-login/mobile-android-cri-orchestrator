package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionAbortedOrUnavailable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class IsSessionAbortedOrUnavailableImpl
    @Inject
    constructor(
        private val sessionStore: SessionStore,
    ) : IsSessionAbortedOrUnavailable {
        override fun invoke(): Flow<Boolean> =
            sessionStore.read().map {
                it == null || it.sessionState == Session.State.Aborted
            }
    }
