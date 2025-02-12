package uk.gov.onelogin.criorchestrator.features.session.internalapi

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ActivityScope
@ContributesTo(CriOrchestratorScope::class)
interface SessionComponent {
    fun sessionStore(): SessionStore
}