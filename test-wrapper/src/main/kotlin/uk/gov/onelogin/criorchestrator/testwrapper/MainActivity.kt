package uk.gov.onelogin.criorchestrator.testwrapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.testwrapper.logging.AnalyticsLoggerFactory
import uk.gov.onelogin.criorchestrator.testwrapper.logging.homeScreenViewEvent

class MainActivity : ComponentActivity() {
    private val analyticsLogger by lazy {
        AnalyticsLoggerFactory.createAnalyticsLogger(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val criOrchestratorComponent =
                rememberCriOrchestrator(
                    StubHttpClient(
                        apiResponse = ApiResponse.Offline,
                    ),
                    analyticsLogger = analyticsLogger,
                )
            GdsTheme {
                ProveYourIdentityCard(
                    component = criOrchestratorComponent,
                    modifier = Modifier,
                )
            }
        }
        analyticsLogger.logEvent(true, homeScreenViewEvent(this))
    }
}
