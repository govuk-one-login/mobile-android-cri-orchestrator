package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.SyncIdCheckAnalytics
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.SyncIdCheckScreenId
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class SyncIdCheckScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val analytics =
        SyncIdCheckAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = SyncIdCheckScreenId.SyncIdCheckScreen.rawId,
                    name = context.getString(R.string.loading),
                    params = SyncIdCheckAnalytics.requiredParameters,
                ).asLegacyEvent()

        composeTestRule.setSyncIdCheckScreenContent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    private fun ComposeContentTestRule.setSyncIdCheckScreenContent() {
        setContent {
            SyncIdCheckScreen(
                documentVariety = DocumentVariety.NFC_PASSPORT,
                navController = rememberNavController(),
                viewModel =
                    SyncIdCheckViewModel(
                        launcherDataReader =
                            LauncherDataReader(
                                sessionStore = FakeSessionStore(),
                                biometricTokenReader =
                                    StubBiometricTokenReader(
                                        BiometricTokenResult.Success(
                                            BiometricToken.createTestToken(),
                                        ),
                                    ),
                            ),
                        logger = SystemLogger(),
                        analytics = analytics,
                    ),
            )
        }
    }
}
