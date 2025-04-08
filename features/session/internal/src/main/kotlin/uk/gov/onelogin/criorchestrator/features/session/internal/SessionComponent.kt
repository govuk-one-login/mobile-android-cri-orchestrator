package uk.gov.onelogin.criorchestrator.features.session.internal

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Singleton

@Singleton
@ContributesTo(CriOrchestratorSingletonScope::class)
interface SessionComponent {
    fun sessionStore(): SessionStore
}
