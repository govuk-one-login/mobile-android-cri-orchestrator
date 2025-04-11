package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
class BiometricTokenModule {
    @Provides
    fun provideBiometricApi(
        httpClient: GenericHttpClient,
    ): BiometricApi = BiometricApiImpl(httpClient)

    @Provides
    fun provideBiometricTokenProvider(
        biometricApi: BiometricApiImpl,
        logger: Logger,
    ): BiometricTokenProviderImpl = BiometricTokenProviderImpl(
        biometricApi,
        logger,
    )
}