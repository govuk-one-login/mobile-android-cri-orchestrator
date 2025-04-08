package uk.gov.onelogin.criorchestrator.sdk.publicapi

import android.content.Context
import org.mockito.Mockito.mock
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.StubHttpClient
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

fun CriOrchestratorSdk.Companion.createTestInstance(
    authenticatedHttpClient: GenericHttpClient =
        StubHttpClient(
            apiResponse = ApiResponse.Offline,
        ),
    analyticsLogger: AnalyticsLogger = mock(),
    initialConfig: Config = Config.createTestInstance(),
    logger: Logger = mock(),
    applicationContext: Context = mock(),
): CriOrchestratorSdk =
    this.create(
        authenticatedHttpClient = authenticatedHttpClient,
        analyticsLogger = analyticsLogger,
        initialConfig = initialConfig,
        logger = logger,
        applicationContext = applicationContext,
    )
