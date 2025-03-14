package uk.gov.onelogin.criorchestrator.sdk.internal.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers

@Module
@ContributesTo(CriOrchestratorScope::class)
class CoroutineDispatchersModule {
    @Provides
    fun provideCoroutineDispatchers() = CoroutineDispatchers()
}
