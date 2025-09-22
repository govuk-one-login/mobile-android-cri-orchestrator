package uk.gov.onelogin.criorchestrator.features.session.publicapi

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@ContributesTo(CriOrchestratorAppScope::class)
fun interface RefreshActiveSessionProviders {
    fun refreshActiveSession(): RefreshActiveSession
}

/**
 * Get the [RefreshActiveSession] use case.
 */
val CriOrchestratorSdk.refreshActiveSession: RefreshActiveSession get() =
    (this.appGraph as RefreshActiveSessionProviders).refreshActiveSession()
