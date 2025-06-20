package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.architecture.CriOrchestratorService
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [CriOrchestratorService] that listens for config changes and clears the session store when appropriate.
 *
 * For example, if the backend URL configuration is changed during testing, the session store should be cleared.
 */
@Singleton
@ContributesMultibinding(CriOrchestratorSingletonScope::class, boundType = CriOrchestratorService::class)
class ResetSessionService
    @Inject
    constructor(
        private val sessionStore: SessionStore,
        private val configStore: ConfigStore,
    ) : CriOrchestratorService {
        override suspend fun start() {
            configStore
                .readAll()
                .map {
                    listOf(
                        it[SdkConfigKey.IdCheckAsyncBackendBaseUrl],
                        it[SdkConfigKey.BypassIdCheckAsyncBackend],
                    )
                }.drop(1)
                .distinctUntilChanged()
                .onEach {
                    sessionStore.clear()
                }.collect()
        }
    }
