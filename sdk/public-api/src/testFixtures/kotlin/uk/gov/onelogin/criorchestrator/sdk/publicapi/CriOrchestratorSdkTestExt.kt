package uk.gov.onelogin.criorchestrator.sdk.publicapi

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.Mockito.mock
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.StubHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.internal.CriOrchestratorSingletonImpl
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("LongParameterList")
fun CriOrchestratorSdk.Companion.createTestInstance(
    authenticatedHttpClient: GenericHttpClient =
        StubHttpClient(
            apiResponse = ApiResponse.Offline,
        ),
    analyticsLogger: AnalyticsLogger = mock(),
    initialConfig: Config = Config.createTestInstance(),
    logger: Logger = mock(),
    applicationContext: Context = mock(),
    testDispatcher: CoroutineDispatcher? = UnconfinedTestDispatcher(),
): CriOrchestratorSdk =
    CriOrchestratorSingletonImpl(
        authenticatedHttpClient = authenticatedHttpClient,
        analyticsLogger = analyticsLogger,
        initialConfig = initialConfig,
        logger = logger,
        applicationContext = applicationContext,
        testDispatcher = testDispatcher,
    )
