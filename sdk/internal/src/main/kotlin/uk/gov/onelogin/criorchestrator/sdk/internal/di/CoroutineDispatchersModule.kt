package uk.gov.onelogin.criorchestrator.sdk.internal.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.libraries.kotlinutils.CoroutineDispatchers
import javax.inject.Named

@Module
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
