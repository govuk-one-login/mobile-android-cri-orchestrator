package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
class BiometricTokenModule {
    @Provides
    fun provideBiometricApi(
        configStore: ConfigStore,
        realBiometricApi: BiometricApiImpl,
        fakeBiometricApi: FakeBiometricTokenApi,
    ): BiometricApi =
        if (configStore.readSingle(SdkConfigKey.BypassGetTokenAsyncBackend).value) {
            fakeBiometricApi
        } else {
            realBiometricApi
        }
}
