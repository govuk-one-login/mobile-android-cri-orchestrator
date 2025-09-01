package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.internal.config.fromUserConfig
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSingletonComponent

/**
 * @param authenticatedHttpClient The HTTP client to make all network calls.
 * @param analyticsLogger The analytics logger that will receive events from the SDK.
 * @param userConfig The configuration to initialise the SDK.
 *   Configuration may be updated through the developer menu.
 * @param logger The logger that logs events to the system and to Crashlytics.
 * @param applicationContext The host application
 * @param testDispatcher Coroutine dispatcher to use in place of
 *   IO, Default and Unconfined dispatchers.
 */
class CriOrchestratorSingletonImpl(
    authenticatedHttpClient: GenericHttpClient,
    analyticsLogger: AnalyticsLogger,
    userConfig: Config,
    logger: Logger,
    applicationContext: Context,
    testDispatcher: CoroutineDispatcher? = null,
) : CriOrchestratorSdk {
    private val _component: BaseCriOrchestratorSingletonComponent =
        createGraphFactory<BaseCriOrchestratorSingletonComponent.Factory>()
            .create(
                authenticatedHttpClient = authenticatedHttpClient,
                analyticsLogger = analyticsLogger,
                initialConfig = Config.fromUserConfig(userConfig),
                logger = logger,
                applicationContext = applicationContext,
                testDispatcher = testDispatcher,
            )
    override val component: CriOrchestratorSingletonComponent = _component as CriOrchestratorSingletonComponent

    init {
        with(_component.coroutineScope()) {
            _component.services().forEach { service ->
                launch {
                    service.start()
                }
            }
        }
    }
}
