package uk.gov.onelogin.criorchestrator.sdk.sharedapi

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

/**
 * Public-facing abstraction that hides the internal dependency graph
 * (`BaseCriOrchestratorSdkGraph`) and provides no functionality.
 */
@Deprecated(
    "Renamed to CriOrchestratorAppGraph. To be removed 15/11/2025.",
    replaceWith = ReplaceWith("CriOrchestratorAppGraph"),
)
interface CriOrchestratorSingletonComponent

/**
 * Public-facing abstraction that hides the internal dependency graph
 * (`BaseCriOrchestratorSdkGraph`) and provides no functionality.
 */
@ContributesTo(CriOrchestratorAppScope::class)
interface CriOrchestratorAppGraph : CriOrchestratorSingletonComponent
