package uk.gov.onelogin.criorchestrator.libraries.di.modules

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope
import javax.inject.Named

/**
 * Module for injecting dispatchers. This is so that tests can be run with the appropriate test
 * dispatcher.
 */
@Module
@ContributesTo(CriOrchestratorScope::class)
object DispatcherModule {
    const val IO_DISPATCHER_NAME = "IoDispatcher"

    @Provides
    @Named(IO_DISPATCHER_NAME)
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
