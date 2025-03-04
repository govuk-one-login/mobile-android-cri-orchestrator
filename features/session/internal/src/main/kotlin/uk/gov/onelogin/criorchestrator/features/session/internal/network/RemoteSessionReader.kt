package uk.gov.onelogin.criorchestrator.features.session.internal.network

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.session.internal.network.response.ActiveSessionApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internal.network.session.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionReader
import uk.gov.onelogin.criorchestrator.libraries.di.modules.DispatcherModule.IO_DISPATCHER_NAME
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class, boundType = SessionReader::class)
class RemoteSessionReader
    @Inject
    constructor(
        private val configStore: ConfigStore,
        @Named(IO_DISPATCHER_NAME)
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
        private val sessionStore: SessionStore,
        private val sessionApi: SessionApi,
        private val logger: Logger,
    ) : SessionReader,
        LogTagProvider {
        override fun isActiveSession(): Flow<Boolean> =
            configStore
                .read(IdCheckAsyncBackendBaseUrl)
                .flowOn(dispatcher)
                .map {
                    sessionApi.getActiveSession()
                }.onEach {
                    logResponse(it)
                }.map {
                    parseSession(it)
                }.distinctUntilChanged()
                .onEach {
                    sessionStore.write(it)
                }.map { session ->
                    session != null
                }

        private fun parseSession(response: ApiResponse): Session? {
            if (response !is ApiResponse.Success<*>) {
                return null
            }

            return try {
                val parsedResponse: ActiveSessionApiResponse.ActiveSessionSuccess =
                    Json.decodeFromString(response.response.toString())
                Session(
                    sessionId = parsedResponse.sessionId,
                    redirectUri = parsedResponse.redirectUri,
                    state = parsedResponse.state,
                )
            } catch (e: IllegalArgumentException) {
                logger.error(tag, "Failed to parse active session response", e)
                null
            }
        }

        private fun logResponse(response: ApiResponse) {
            when (response) {
                is ApiResponse.Success<*> ->
                    logger.debug(tag, "Got active session")

                is ApiResponse.Failure ->
                    logger.error(tag, "Failed to fetch active session", response.error)

                ApiResponse.Loading ->
                    logger.debug(tag, "Loading ... fetching active session ...")

                ApiResponse.Offline ->
                    logger.debug(tag, "Failed to fetch active session - device is offline")
            }
        }
    }
