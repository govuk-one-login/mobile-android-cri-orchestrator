package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import android.content.Context
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.logging.api.v3dot1.logger.asLegacyEvent
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.v3dot1.model.ViewEvent
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.testing.MainStandardDispatcherRule
import uk.gov.onelogin.criorchestrator.libraries.testing.ReportingAnalyticsLoggerRule
import kotlin.test.assertContains

@RunWith(AndroidJUnit4::class)
class SelectDrivingLicenceAnalyticsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mainStandardDispatcherRule = MainStandardDispatcherRule()

    @get:Rule
    val reportingAnalyticsLoggerRule = ReportingAnalyticsLoggerRule()
    private val analyticsLogger = reportingAnalyticsLoggerRule.analyticsLogger
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val readMoreButton = hasText(context.getString(R.string.selectdocument_drivinglicence_readmore_button))
    private val continueButton = hasText(context.getString(R.string.selectdocument_drivinglicence_continuebutton))
    private val yesOption = hasText(context.getString(R.string.selectdocument_drivinglicence_selection_yes))

    private val configStore: ConfigStore = mock()
    private val nfcChecker = mock<NfcChecker>()
    private val navController: NavController = mock()

    private val analytics =
        SelectDocAnalytics(
            resourceProvider =
                AndroidResourceProvider(
                    context = context,
                ),
            analyticsLogger = analyticsLogger,
        )

    private val viewModel: SelectDrivingLicenceViewModel by lazy {
        spy(
            SelectDrivingLicenceViewModel(
                analytics = analytics,
                nfcChecker = nfcChecker,
                configStore = configStore,
            ),
        )
    }

    @Before
    fun setup() {
        whenever(configStore.readSingle(NfcConfigKey.NfcAvailability)).thenReturn(
            Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
        )
        given(nfcChecker.hasNfc()).willReturn(false)
    }

    @Test
    fun `when screen is started, it tracks analytics`() {
        val expectedEvent =
            ViewEvent
                .Screen(
                    id = SelectDocScreenId.SelectDrivingLicence.rawId,
                    name = context.getString(R.string.selectdocument_drivinglicence_title),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()

        composeTestRule.setSelectDrivingLicenceContent()

        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when continue button is clicked, it tracks analytics`() {
        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Form(
                    text = context.getString(R.string.selectdocument_drivinglicence_continuebutton),
                    params = SelectDocAnalytics.requiredParameters,
                    response = context.getString(R.string.selectdocument_drivinglicence_selection_yes),
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    @Test
    fun `when read more is clicked, it tracks analytics`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(readMoreButton)
            .performClick()

        val expectedEvent =
            TrackEvent
                .Button(
                    text = context.getString(R.string.selectdocument_drivinglicence_readmore_button),
                    params = SelectDocAnalytics.requiredParameters,
                ).asLegacyEvent()
        assertContains(analyticsLogger.loggedEvents, expectedEvent)
    }

    private fun ComposeContentTestRule.setSelectDrivingLicenceContent() {
        setContent {
            SelectDrivingLicenceScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}
