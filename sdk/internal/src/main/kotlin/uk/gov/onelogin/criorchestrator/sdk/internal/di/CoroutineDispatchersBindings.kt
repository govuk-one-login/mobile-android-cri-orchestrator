package uk.gov.onelogin.criorchestrator.sdk.internal.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers

@BindingContainer
@ContributesTo(CriOrchestratorAppScope::class)
class CoroutineDispatchersBindings {
    @Provides
    fun provideCoroutineDispatchers(
        @Named(TEST_DISPATCHER_NAME)
        testDispatcher: CoroutineDispatcher?,
    ): CoroutineDispatchers =
        CoroutineDispatchers.defaultUnlessTest(
            testDispatcher = testDispatcher,
        )

    companion object {
        const val TEST_DISPATCHER_NAME = "testDispatcher"
    }
}
