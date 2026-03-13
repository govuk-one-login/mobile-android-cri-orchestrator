package uk.gov.onelogin.criorchestrator.testwrapper.di

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.collections.immutable.persistentListOf
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.testwrapper.TestWrapperConfig
import uk.gov.onelogin.criorchestrator.testwrapper.hilt.CriOrchestratorSdkConfigHiltModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CriOrchestratorSdkConfigHiltModule::class],
)
object TestCriOrchestratorSdkConfigHiltModule {
    @Provides
    fun provideInitialConfig(resources: Resources) =
        TestWrapperConfig
            .baseConfig(resources)
            .combinedWith(
                Config(
                    entries =
                        persistentListOf(
                            // Disable secure store (and shared preferences) for Robolectric environment
                            Config.Entry(
                                key = SdkConfigKey.EnableSecureStore,
                                Config.Value.BooleanValue(
                                    value = false,
                                ),
                            ),
                        ),
                ),
            )
}
