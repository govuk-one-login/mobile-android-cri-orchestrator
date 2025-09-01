package uk.gov.onelogin.criorchestrator.sdk.internal.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers

@BindingContainer
@ContributesTo(CriOrchestratorSingletonScope::class)
class CoroutineDispatchersModule {
    @Provides
    fun provideCoroutineDispatchers(
        @Named(TEST_DISPATCHER_NAME)
        testDispatcher: CoroutineDispatcher?,
    ): CoroutineDispatchers {
        if (testDispatcher != null) {
            return CoroutineDispatchers(
                default = testDispatcher,
                io = testDispatcher,
                main = Dispatchers.Main,
                unconfined = testDispatcher,
            )
        }

        return CoroutineDispatchers()
    }

    companion object {
        const val TEST_DISPATCHER_NAME = "testDispatcher"
    }
}
