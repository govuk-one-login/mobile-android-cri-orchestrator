package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricApiImpl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.FakeBiometricTokenApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class, binding = binding<BiometricApi>())
class ConfigurableBiometricApi
    @Inject
    constructor(
        private val configStore: ConfigStore,
        private val realBiometricApi: Provider<BiometricApiImpl>,
        private val fakeBiometricApi: Provider<FakeBiometricTokenApi>,
    ) : BiometricApi {
        override suspend fun getBiometricToken(
            sessionId: String,
            documentVariety: DocumentVariety,
        ): ApiResponse =
            if (configStore.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
                fakeBiometricApi().getBiometricToken(sessionId, documentVariety)
            } else {
                realBiometricApi().getBiometricToken(sessionId, documentVariety)
            }
    }
