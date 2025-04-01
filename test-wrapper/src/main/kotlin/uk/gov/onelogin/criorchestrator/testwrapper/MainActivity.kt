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

class MainActivity : ComponentActivity() {
    private val logger = LoggerFactory.createLogger()

    private val analyticsLogger by lazy {
        AnalyticsLoggerFactory.createAnalyticsLogger(this, logger)
    }
    private lateinit var httpClient: GenericHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        httpClient =
            createHttpClient(
                resources,
                "mock_subject_token",
            )

        val config = TestWrapperConfig.provideConfig(resources)
        setContent {
            GdsTheme {
                MainContent(
                    httpClient = httpClient,
                    analyticsLogger = analyticsLogger,
                    config = config,
                    logger = logger,
                    didUpdateSub = { sub ->
                        httpClient = createHttpClient(resources, sub)
                    },
                )
            }
        }
        analyticsLogger.logEvent(true, homeScreenViewEvent(this))
    }
}
