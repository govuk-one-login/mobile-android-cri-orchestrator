package uk.gov.onelogin.criorchestrator.sdk.internal

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Singleton

@Module
@ContributesTo(CriOrchestratorSingletonScope::class)
object SingletonCoroutineScopeModule {
    @Provides
    @Singleton
    fun singletonCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())
}
