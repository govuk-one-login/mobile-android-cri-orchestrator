package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations

@RunWith(AndroidJUnit4::class)
class SelectDrivingLicenceScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private lateinit var readMoreButton: SemanticsMatcher
    private lateinit var yesOption: SemanticsMatcher
    private lateinit var noOption: SemanticsMatcher
    private lateinit var continueButton: SemanticsMatcher

    private val navController: NavController = mock()
    private val configStore: ConfigStore = mock()
    private val nfcChecker = mock<NfcChecker>()

    private val viewModel: SelectDrivingLicenceViewModel by lazy {
        spy(
            SelectDrivingLicenceViewModel(
                analytics = mock(),
                nfcChecker = nfcChecker,
                configStore = configStore,
            ),
        )
    }

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        readMoreButton =
            hasText(context.getString(R.string.selectdocument_drivinglicence_readmore_button))
        yesOption = hasText(context.getString(R.string.selectdocument_drivinglicence_selection_yes))
        noOption = hasText(context.getString(R.string.selectdocument_drivinglicence_selection_no))
        continueButton =
            hasText(context.getString(R.string.selectdocument_drivinglicence_continuebutton))
        whenever(configStore.readSingle(NfcConfigKey.NfcAvailability)).thenReturn(
            Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
        )
        given(nfcChecker.hasNfc()).willReturn(false)
    }

    @Test
    fun `when screen is started, no item is selected`() =
        runTest {
            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(yesOption, useUnmergedTree = true)
                .onSibling()
                .assertIsNotSelected()

            composeTestRule
                .onNode(noOption, useUnmergedTree = true)
                .onSibling()
                .assertIsNotSelected()
        }

    @Test
    fun `when screen is started and nfc is not enabled, the read more button is not displayed`() =
        runTest {
            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(readMoreButton)
                .assertDoesNotExist()
        }

    @Test
    fun `when nfc is enabled and read more is selected, it navigates to types of ID`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(true)

            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(readMoreButton)
                .performClick()

            verify(navController).navigate(SelectDocDestinations.TypesOfPhotoID)
        }

    @Test
    fun `when yes is tapped, it is selected`() {
        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when no is tapped, it is selected`() {
        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .onParent()
            .performClick()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .onParent()
            .onChildAt(0)
            .assertIsSelected()
    }

    @Test
    fun `when user has not made a choice, the confirm button is disabled`() {
        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(continueButton)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .assertIsNotEnabled()

        verify(viewModel, never()).onContinueClicked(any())
        verifyNoInteractions(navController)
    }

    @Test
    fun `when yes is selected and confirm is tapped, it navigates to confirm driving licence`() =
        runTest {
            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(yesOption, useUnmergedTree = true)
                .performClick()

            composeTestRule
                .onNode(continueButton)
                .assertIsEnabled()
                .performClick()

            verify(navController).navigate(SelectDocDestinations.ConfirmDrivingLicence)
        }

    @Test
    fun `when no is selected and nfc is enabled, it navigates to confirm no chipped ID`() =
        runTest {
            composeTestRule.setSelectDrivingLicenceContent()

            given(nfcChecker.hasNfc()).willReturn(true)

            composeTestRule
                .onNode(noOption, useUnmergedTree = true)
                .assertIsEnabled()
                .performClick()

            composeTestRule
                .onNode(continueButton)
                .assertIsEnabled()
                .performClick()

            verify(navController).navigate(SelectDocDestinations.ConfirmNoChippedID)
        }

    @Test
    fun `when no is selected and nfc is not enabled, it navigates to confirm no non chipped ID`() =
        runTest {
            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(noOption, useUnmergedTree = true)
                .assertIsEnabled()
                .performClick()

            composeTestRule
                .onNode(continueButton)
                .assertIsEnabled()
                .performClick()

            verify(navController).navigate(SelectDocDestinations.ConfirmNoNonChippedID)
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
