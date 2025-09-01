package uk.gov.onelogin.criorchestrator.sdk.internal

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@BindingContainer
@ContributesTo(CriOrchestratorSingletonScope::class)
object SingletonCoroutineScopeModule {
    @Provides
    @SingleIn(CriOrchestratorSingletonScope::class)
    fun singletonCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())
}
