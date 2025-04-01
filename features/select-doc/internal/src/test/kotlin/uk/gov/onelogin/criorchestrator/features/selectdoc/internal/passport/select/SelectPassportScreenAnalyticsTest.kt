package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
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
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class SelectPassportScreenAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val readMoreButton = hasText(context.getString(R.string.selectdocument_passport_readmore_button))
    private val continueButton = hasText(context.getString(R.string.selectdocument_passport_continuebutton))
    private val yesOption = hasText(context.getString(R.string.selectdocument_passport_selection_yes))
    private val image = hasContentDescription(context.getString(R.string.selectdocument_passport_imagedescription))

    private val analytics =
        SelectDocAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel =
        SelectPassportViewModel(
            analytics = analytics,
        )

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = SelectDocumentScreenId.SelectPassport.rawId,
                    name = context.getString(R.string.selectdocument_passport_title),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()
        composeTestRule.setSelectPassportScreenContent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `given selection is made, when continue button is clicked, it tracks analytics`() {
        composeTestRule.setSelectPassportScreenContent()

        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Form(
                    text = context.getString(R.string.selectdocument_passport_continuebutton),
                    params = SelectDocAnalytics.requiredParameters,
                    response = context.getString(R.string.selectdocument_passport_selection_yes),
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when read more is clicked, it tracks analytics`() {
        composeTestRule.setSelectPassportScreenContent()

        swipeToAdditionalContent()

        composeTestRule
            .onNode(readMoreButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.selectdocument_passport_readmore_button),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    private fun ComposeContentTestRule.setSelectPassportScreenContent() {
        setContent {
            SelectPassportScreen(
                viewModel = viewModel,
                navController = rememberNavController(),
            )
        }
    }

    private fun swipeToAdditionalContent() {
        composeTestRule
            .onNode(
                image,
            ).onParent()
            .performTouchInput {
                swipeUp()
            }
    }
}
