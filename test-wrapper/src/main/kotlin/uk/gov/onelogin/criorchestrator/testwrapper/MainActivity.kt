package uk.gov.onelogin.criorchestrator.testwrapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.testwrapper.logging.homeScreenViewEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val objects = App.instance.objects

        setContent {
            GdsTheme {
                MainContent(
                    criOrchestratorSdk = objects.criOrchestratorSdk,
                    onSubUpdateRequest = { objects.subjectToken = it },
                )
            }
        }
        objects.analyticsLogger.logEvent(true, homeScreenViewEvent(this))
    }
}
