package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

@ContributesBinding(CriOrchestratorAppScope::class, binding = binding<ActiveSessionApi>())
class ConfigurableActiveSessionApi(
    private val configStore: ConfigStore,
    private val realActiveSessionApi: Provider<ActiveSessionApiImpl>,
    private val fakeActiveSessionApi: Provider<FakeActiveSessionApi>,
) : ActiveSessionApi {
    override suspend fun getActiveSession(): ApiResponse =
        if (configStore.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
            fakeActiveSessionApi()
        } else {
            realActiveSessionApi()
        }.getActiveSession()
}
