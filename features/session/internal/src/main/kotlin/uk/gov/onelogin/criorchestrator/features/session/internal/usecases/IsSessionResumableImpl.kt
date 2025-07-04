package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class)
class IsSessionResumableImpl
    @Inject
    constructor(
        private val sessionStore: SessionStore,
    ) : IsSessionResumable {
        override fun invoke(): Flow<Boolean> =
            sessionStore.read().map {
                it != null && it.sessionState == Session.State.Created
            }
    }
