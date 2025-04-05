package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm

import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class ConfirmBrpScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val continueButton =
        hasText(context.getString(R.string.confirmdocument_confirmbutton))

    private val analytics =
        SelectDocAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel =
        ConfirmBrpViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = SelectDocScreenId.ConfirmBrp.rawId,
                    name = context.getString(R.string.confirmdocument_brp_title),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()
        composeTestRule.setConfirmBrpScreenContent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when continue button is clicked, it tracks analytics`() {
        composeTestRule.setConfirmBrpScreenContent()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.confirmdocument_confirmbutton),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    private fun ComposeContentTestRule.setConfirmBrpScreenContent() {
        setContent {
            ConfirmBrpScreen(
                viewModel = viewModel,
                navController = rememberNavController(),
            )
        }
    }
}
