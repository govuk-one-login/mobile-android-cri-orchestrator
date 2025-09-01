package uk.gov.onelogin.criorchestrator.features.session.internal

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@ContributesTo(CriOrchestratorSingletonScope::class)
interface SessionComponent {
    fun sessionStore(): SessionStore
}
