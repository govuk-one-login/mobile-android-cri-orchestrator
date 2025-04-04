package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentSetOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModelModule
import uk.gov.onelogin.criorchestrator.features.session.internalapi.StubSessionReader
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class ProveYourIdentityCardAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val viewModel =
        ProveYourIdentityViewModel(
            analytics =
                ResumeAnalytics(
                    resourceProvider = AndroidResourceProvider(context),
                    analyticsLogger = analyticsLogger,
                ),
            sessionReader = StubSessionReader(),
            logger = SystemLogger(),
        )

    private val modal: SemanticsMatcher = hasTestTag(ProveYourIdentityRootTestTags.MODAL)

    // FIXME This content description should be "Cancel button"
    //  https://govukverify.atlassian.net/browse/DCMAW-12003
    private val closeButton = hasContentDescription("Close Button")
    private val continueButton = hasText(context.getString(R.string.start_id_check_primary_button))

    @Test
    fun `when card continue button is clicked, it sends analytics`() {
        composeTestRule.displayProveYourIdentityRoot()

        composeTestRule
            .onNode(closeButton)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        composeTestRule
            .onNode(modal)
            .assertIsDisplayed()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.start_id_check_primary_button),
                    params = ResumeAnalytics.requiredParameters,
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    private fun ComposeContentTestRule.displayProveYourIdentityRoot() =
        setContent {
            ProveYourIdentityRoot(
                viewModel,
                persistentSetOf(
                    ContinueToProveYourIdentityNavGraphProvider(
                        ContinueToProveYourIdentityViewModelModule.provideFactory(
                            analytics = mock(),
                            nfcChecker = mock(),
                            configStore = mock(),
                        ),
                    ),
                ),
            )
        }
}
