package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesHiltModule {
    const val COROUTINE_SCOPE_NAME = "uk.gov.onelogin.criorchestrator.testwrapper"

    @Provides
    @Singleton
    @Named(COROUTINE_SCOPE_NAME)
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())
}
