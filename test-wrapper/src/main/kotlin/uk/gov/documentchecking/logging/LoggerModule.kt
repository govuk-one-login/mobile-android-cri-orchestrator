package uk.gov.documentchecking.logging

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.logging.api.Logger
import uk.gov.logging.testdouble.SystemLogger
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LoggerModule {
    @Provides
    @Singleton
    fun providesLogger(): Logger = SystemLogger()
}
