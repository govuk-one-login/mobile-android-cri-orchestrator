package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class SelectBrpScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val resourceProvider = AndroidResourceProvider(context = context)
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val readMore = hasText(context.getString(SelectBrpConstants.readMoreButtonTextId))
    private val yesOption = hasText(context.getString(SelectBrpConstants.selectionItems[0]))
    private val noOption = hasText(context.getString(SelectBrpConstants.selectionItems[1]))
    private val continueButton = hasText(context.getString(SelectBrpConstants.continueButtonTextId))

    private val analytics =
        SelectDocumentAnalytics(
            resourceProvider = resourceProvider,
            analyticsLogger = analyticsLogger,
        )

    private val viewModel = SelectBrpViewModel(analytics)

    @Before
    fun setup() {
        composeTestRule.setContent {
            SelectBrpScreen(
                viewModel,
                navController = mock(),
            )
        }
    }

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = SelectDocumentScreenId.SelectBrp.rawId,
                    name = context.getString(SelectBrpConstants.titleId),
                    params = SelectDocumentAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when read more is clicked, it tracks analytics`() {
        swipeToAdditionalContent()
        composeTestRule
            .onNode(readMore)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(SelectBrpConstants.readMoreButtonTextId),
                    params = SelectDocumentAnalytics.requiredParameters,
                ).asLegacyEvent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `given yes is selected, when continue is clicked, it tracks analytics`() =
        runTest {
            swipeToAdditionalContent()

            composeTestRule
                .onNode(yesOption, useUnmergedTree = true)
                .performClick()

            composeTestRule
                .onNode(continueButton, useUnmergedTree = true)
                .performClick()

            val expectedEvent =
                TrackEvent
                    .Form(
                        text = context.getString(SelectBrpConstants.continueButtonTextId),
                        params = SelectDocumentAnalytics.requiredParameters,
                        response = context.getString(SelectBrpConstants.selectionItems[0]),
                    ).asLegacyEvent()

            assertContains(analyticsLogger.loggedEvents, expectedEvent)
        }

    @Test
    fun `given no is selected, when continue is clicked, it tracks analytics`() =
        runTest {
            swipeToAdditionalContent()

            composeTestRule
                .onNode(noOption, useUnmergedTree = true)
                .performClick()

            composeTestRule
                .onNode(continueButton, useUnmergedTree = true)
                .performClick()

            val expectedEvent =
                TrackEvent
                    .Form(
                        text = context.getString(SelectBrpConstants.continueButtonTextId),
                        params = SelectDocumentAnalytics.requiredParameters,
                        response = context.getString(SelectBrpConstants.selectionItems[1]),
                    ).asLegacyEvent()

            assertContains(analyticsLogger.loggedEvents, expectedEvent)
        }

    private fun swipeToAdditionalContent() {
        composeTestRule
            .onNode(readMore)
            .onParent()
            .performTouchInput {
                swipeUp()
            }
    }
}
