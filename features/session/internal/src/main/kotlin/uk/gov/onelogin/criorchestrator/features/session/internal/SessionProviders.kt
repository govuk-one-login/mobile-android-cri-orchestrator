package uk.gov.onelogin.criorchestrator.features.session.internal

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

@ContributesTo(CriOrchestratorAppScope::class)
interface SessionProviders {
    fun sessionStore(): SessionStore
}
