package uk.gov.onelogin.criorchestrator.testwrapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.testwrapper.logging.AnalyticsLoggerFactory
import uk.gov.onelogin.criorchestrator.testwrapper.logging.LoggerFactory
import uk.gov.onelogin.criorchestrator.testwrapper.logging.homeScreenViewEvent
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient
import javax.inject.Provider

class MainActivity : ComponentActivity() {
    private val logger = LoggerFactory.createLogger()

    private val analyticsLogger by lazy {
        AnalyticsLoggerFactory.createAnalyticsLogger(this, logger)
    }
    private var subjectToken: String? = null
    private val httpClient: GenericHttpClient by lazy {
        createHttpClient(
            subjectToken = Provider { subjectToken },
            resources = resources,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = TestWrapperConfig.provideConfig(resources)

        setContent {
            GdsTheme {
                MainContent(
                    analyticsLogger = analyticsLogger,
                    config = config,
                    logger = logger,
                    httpClient = httpClient,
                    onSubUpdateRequest = { subjectToken = it },
                )
            }
        }
        analyticsLogger.logEvent(true, homeScreenViewEvent(this))
    }
}
