package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricApiImpl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.FakeBiometricTokenApi
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class, boundType = BiometricApi::class)
class ConfigurableBiometricApi
    @Inject
    constructor(
        private val configStore: ConfigStore,
        private val realBiometricApi: BiometricApiImpl,
        private val fakeBiometricApi: FakeBiometricTokenApi,
    ) : BiometricApi {
        override suspend fun getBiometricToken(
            sessionId: String,
            documentVariety: DocumentVariety,
        ): ApiResponse =
            if (configStore.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
                fakeBiometricApi.getBiometricToken(sessionId, documentVariety)
            } else {
                realBiometricApi.getBiometricToken(sessionId, documentVariety)
            }
    }
