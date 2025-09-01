package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@BindingContainer
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
