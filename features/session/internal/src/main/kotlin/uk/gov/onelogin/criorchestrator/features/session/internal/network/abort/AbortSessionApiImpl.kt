package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class)
class AbortSessionApiImpl
    @Inject
    constructor(
        private val config: ConfigStore,
        private val realAbortSessionApi: RealAbortSessionApi,
        private val fakeAbortSessionApi: FakeAbortSessionApi,
    ) : AbortSessionApi {
        override suspend fun abortSession(sessionId: String): ApiResponse =
            if (config.readSingle(SdkConfigKey.BypassIdCheckAsyncBackend).value) {
                fakeAbortSessionApi
            } else {
                realAbortSessionApi
            }.abortSession(sessionId)
    }
