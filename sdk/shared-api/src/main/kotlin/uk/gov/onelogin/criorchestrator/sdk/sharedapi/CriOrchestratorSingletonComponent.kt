package uk.gov.onelogin.criorchestrator.sdk.sharedapi

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

/**
 * Public-facing abstraction that hides the internal Dagger component
 * (`BaseCriOrchestratorSingletonComponent`) and provides no functionality.
 */
@ContributesTo(CriOrchestratorSingletonScope::class)
interface CriOrchestratorSingletonComponent
