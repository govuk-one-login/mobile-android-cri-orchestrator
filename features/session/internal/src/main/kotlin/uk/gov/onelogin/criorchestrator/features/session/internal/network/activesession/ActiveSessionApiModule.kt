package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@Module
@ContributesTo(CriOrchestratorSingletonScope::class)
class ActiveSessionApiModule {
    @Provides
    fun providesActiveSessionApi(
        config: ConfigStore,
        realActiveSessionApi: ActiveSessionApiImpl,
        fakeActiveSessionApi: FakeActiveSessionApi,
    ): ActiveSessionApi =
        if (config.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
            fakeActiveSessionApi
        } else {
            realActiveSessionApi
        }
}
