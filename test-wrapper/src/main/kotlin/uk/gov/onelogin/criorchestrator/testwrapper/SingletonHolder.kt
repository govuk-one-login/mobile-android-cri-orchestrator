package uk.gov.onelogin.criorchestrator.testwrapper

import android.app.Application
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.logging.AnalyticsLoggerFactory
import uk.gov.onelogin.criorchestrator.testwrapper.logging.LoggerFactory
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient
import javax.inject.Provider

class SingletonHolder(
    private val application: Application,
) {
    private val resources get() = application.resources

    val logger = LoggerFactory.createLogger()

    val analyticsLogger =
        AnalyticsLoggerFactory.createAnalyticsLogger(application, logger)

    var subjectToken: String? = null

    private val httpClient: GenericHttpClient =
        createHttpClient(
            subjectToken = Provider { subjectToken },
            resources = resources,
        )

    val criOrchestratorSdk =
        CriOrchestratorSdk.create(
            authenticatedHttpClient = httpClient,
            analyticsLogger = analyticsLogger,
            initialConfig = TestWrapperConfig.provideConfig(resources),
            logger = logger,
            applicationContext = application,
        )
}
