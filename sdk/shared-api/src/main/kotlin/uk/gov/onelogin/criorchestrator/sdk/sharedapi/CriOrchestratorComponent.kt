package uk.gov.onelogin.criorchestrator.sdk.sharedapi

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope

/**
 * Public-facing abstraction that hides the internal Dagger component `BaseCriOrchestratorComponent`
 * and provides no functionality.
 */
@ContributesTo(CriOrchestratorScope::class)
interface CriOrchestratorComponent
