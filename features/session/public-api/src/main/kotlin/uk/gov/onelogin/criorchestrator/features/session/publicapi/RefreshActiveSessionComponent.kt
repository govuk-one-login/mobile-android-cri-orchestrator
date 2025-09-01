package uk.gov.onelogin.criorchestrator.features.session.publicapi

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@ContributesTo(CriOrchestratorSingletonScope::class)
fun interface RefreshActiveSessionComponent {
    fun refreshActiveSession(): RefreshActiveSession
}

/**
 * Get the [RefreshActiveSession] use case.
 */
val CriOrchestratorSdk.refreshActiveSession: RefreshActiveSession get() =
    (this.component as RefreshActiveSessionComponent).refreshActiveSession()
