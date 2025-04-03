package uk.gov.onelogin.criorchestrator.features.session.internal.network

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@Module
@ContributesTo(CriOrchestratorScope::class)
class SessionApiModule {
    @Provides
    fun providesSessionApi(
        config: ConfigStore,
        realSessionApi: SessionApiImpl,
        fakeSessionApi: FakeSessionApi,
    ): SessionApi =
        if (config.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
            fakeSessionApi
        } else {
            realSessionApi
        }
}
