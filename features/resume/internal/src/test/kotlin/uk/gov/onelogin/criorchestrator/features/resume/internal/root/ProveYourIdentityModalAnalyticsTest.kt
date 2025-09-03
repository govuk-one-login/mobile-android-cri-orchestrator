package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModel
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import uk.gov.onelogin.criorchestrator.libraries.testing.viewmodel.TestViewModelProviderFactory
import uk.gov.onelogin.criorchestrator.libraries.testing.viewmodel.testViewModelProvider
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityModalAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val viewModel =
        ProveYourIdentityViewModel.createTestInstance(
            analytics =
                ResumeAnalytics(
                    resourceProvider = AndroidResourceProvider(context),
                    analyticsLogger = analyticsLogger,
                ),
        )

    private val closeButton = hasContentDescription("Close")

    @Test
    fun `when modal close button is clicked, it sends analytics`() {
        composeTestRule.displayProveYourIdentityRoot()

        composeTestRule
            .onNode(closeButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.cancel_button_analytics_text),
                    params = ResumeAnalytics.requiredParameters,
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    private fun ComposeContentTestRule.displayProveYourIdentityRoot() {
        val navGraphProviders =
            persistentSetOf(
                ContinueToProveYourIdentityNavGraphProvider(
                    viewModelProviderFactory =
                        TestViewModelProviderFactory(
                            testViewModelProvider {
                                ContinueToProveYourIdentityViewModel(
                                    analytics = mock(),
                                    nfcChecker = mock(),
                                )
                            },
                        ),
                ),
            )

        setContent {
            ProveYourIdentityRoot(
                viewModel = viewModel,
                navGraphProviders = navGraphProviders,
            )
        }
    }
}
