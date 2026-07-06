package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import dev.zacsweers.metro.ContributesBinding
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkingException
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class AbortSessionApiImpl(
    private val config: ConfigStore,
    private val realAbortSessionApi: RealAbortSessionApi,
    private val fakeAbortSessionApi: FakeAbortSessionApi,
) : AbortSessionApi {
    override suspend fun abortSession(sessionId: String): ApiResponse<String, NetworkingException> =
        if (config.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
            fakeAbortSessionApi
        } else {
            realAbortSessionApi
        }.abortSession(sessionId)
}
