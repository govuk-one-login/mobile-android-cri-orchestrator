package uk.gov.onelogin.criorchestrator.sdk.sharedapi

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

/**
 * Public-facing abstraction that hides the internal Dagger component `BaseCriOrchestratorComponent`
 * and provides no functionality.
 */
@ContributesTo(CriOrchestratorScope::class)
interface CriOrchestratorComponent
