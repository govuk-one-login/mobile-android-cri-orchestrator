package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import dev.zacsweers.metro.ContributesBinding
import uk.gov.android.network.service.v2.NetworkServiceResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class AbortSessionApiImpl(
    private val config: ConfigStore,
    private val realAbortSessionApi: RealAbortSessionApi,
    private val fakeAbortSessionApi: FakeAbortSessionApi,
) : AbortSessionApi {
    override suspend fun abortSession(sessionId: String): NetworkServiceResponse =
        if (config.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
            fakeAbortSessionApi
        } else {
            realAbortSessionApi
        }.abortSession(sessionId)
}
