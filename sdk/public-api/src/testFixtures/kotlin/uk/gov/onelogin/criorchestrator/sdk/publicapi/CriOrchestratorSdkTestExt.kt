package uk.gov.onelogin.criorchestrator.sdk.publicapi

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.Mockito.mock
import uk.gov.android.network.service.NetworkService
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.internal.CriOrchestratorSingletonImpl
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("LongParameterList")
fun CriOrchestratorSdk.Companion.createTestInstance(
    authenticatedHttpClient: NetworkService = mock(),
    analyticsLogger: AnalyticsLogger = mock(),
    initialConfig: Config = Config.createTestInstance(),
    logger: Logger = mock(),
    applicationContext: Context = mock(),
    testDispatcher: CoroutineDispatcher? = UnconfinedTestDispatcher(),
): CriOrchestratorSdk =
    CriOrchestratorSingletonImpl(
        authenticatedHttpClient = authenticatedHttpClient,
        analyticsLogger = analyticsLogger,
        userConfig = initialConfig,
        logger = logger,
        applicationContext = applicationContext,
        testDispatcher = testDispatcher,
    )
