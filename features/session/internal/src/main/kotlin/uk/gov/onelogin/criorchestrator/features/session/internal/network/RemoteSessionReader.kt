package uk.gov.onelogin.criorchestrator.features.session.internal.network

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        private val _isActiveSessionStateFlow = MutableStateFlow<Boolean>(false)

        override val isActiveSessionStateFlow: StateFlow<Boolean> =
            _isActiveSessionStateFlow.asStateFlow()

        override fun handleUpdatedSessionResponse() {
            CoroutineScope(dispatcher).launch {
                configStore.read(IdCheckAsyncBackendBaseUrl).collect { url ->
                    logger.debug(
                        tag,
                        "Collected URL of $url",
                    )
                    val response = sessionApi.getActiveSession()
                    handleResponse(response)
                }
            }
        }

        fun handleResponse(response: ApiResponse) {
            when (response) {
                is ApiResponse.Success<*> -> handleSuccessResponse(response)
                is ApiResponse.Failure -> handleFailureResponse(response)
                ApiResponse.Loading -> handleLoadingResponse()
                ApiResponse.Offline -> handleOfflineResponse()
            }
        }

        private fun handleSuccessResponse(response: ApiResponse.Success<*>) =
            try {
                logger.debug(tag, "Got active session")
                val parsedResponse: ActiveSessionApiResponse.ActiveSessionSuccess =
                    Json.decodeFromString(response.response.toString())
                sessionStore.write(
                    Session(
                        sessionId = parsedResponse.sessionId,
                        redirectUri = parsedResponse.redirectUri,
                        state = parsedResponse.state,
                    ),
                )
                _isActiveSessionStateFlow.value = true
            } catch (e: IllegalArgumentException) {
                logger.error(tag, "Failed to parse active session response", e)
                _isActiveSessionStateFlow.value = false
            }

        private fun handleFailureResponse(response: ApiResponse.Failure) {
            logger.error(tag, "Failed to fetch active session", response.error)
            _isActiveSessionStateFlow.value = false
        }

        private fun handleLoadingResponse() {
            logger.debug(tag, "Loading ... fetching active session ...")
            _isActiveSessionStateFlow.value = false
        }

        private fun handleOfflineResponse() {
            logger.debug(tag, "Failed to fetch active session - device is offline")
            _isActiveSessionStateFlow.value = false
        }
    }
