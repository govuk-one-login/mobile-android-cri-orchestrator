package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.onelogin.criorchestrator.testwrapper.TestWrapperConfig

@Module
@InstallIn(SingletonComponent::class)
object CriOrchestratorSdkConfigHiltModule {
    @Provides
    fun provideInitialConfig(resources: Resources) = TestWrapperConfig.baseConfig(resources)
}
