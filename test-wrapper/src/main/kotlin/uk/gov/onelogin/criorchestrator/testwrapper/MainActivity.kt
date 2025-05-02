package uk.gov.onelogin.criorchestrator.testwrapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.logging.homeScreenViewEvent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var criOrchestratorSdk: CriOrchestratorSdk

    @Inject
    lateinit var analyticsLogger: AnalyticsLogger

    @Inject
    lateinit var subjectTokenRepository: SubjectTokenRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GdsTheme {
                MainContent(
                    criOrchestratorSdk = criOrchestratorSdk,
                    onSubUpdateRequest = { subjectTokenRepository.subjectToken = it },
                )
            }
        }
        analyticsLogger.logEvent(true, homeScreenViewEvent(this))
    }
}
