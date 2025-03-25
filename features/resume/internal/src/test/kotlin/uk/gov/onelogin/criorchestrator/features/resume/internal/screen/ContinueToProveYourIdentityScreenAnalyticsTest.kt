package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule

@RunWith(AndroidJUnit4::class)
class ContinueToProveYourIdentityScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private lateinit var primaryButton: SemanticsMatcher
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val analytics =
        ResumeAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel =
        ContinueToProveYourIdentityViewModel(
            analytics = analytics,
            nfcChecker = mock(),
            configStore = mock(),
        )

    @Before
    fun setUp() {
        primaryButton =
            hasText(context.getString(R.string.continue_to_prove_your_identity_screen_button))
    }

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedScreenName =
            context.getString(R.string.continue_to_prove_your_identity_screen_title)
        val expectedScreenId = ResumeScreenId.ContinueToProveYourIdentity.rawId
        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = mock(),
            )
        }
        val matchingEvents =
            analyticsLogger.loggedEvents.filter {
                it.parameters["screen_id"] == expectedScreenId &&
                    it.parameters["screen_name"] == expectedScreenName
            }
        assertEquals(1, matchingEvents.size)
    }

    @Test
    fun `when continue button is clicked, it tracks analytics`() {
        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = mock(),
            )
        }

        composeTestRule
            .onNode(primaryButton)
            .performClick()

        val matchingEvents =
            analyticsLogger.loggedEvents.filter {
                it.parameters["text"] == context.getString(R.string.continue_to_prove_your_identity_screen_button)
            }
        assertEquals(1, matchingEvents.size)
    }
}
