package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

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
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

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
        whenever(configStore.readSingle(NfcConfigKey.StubNcfCheck)).thenReturn(
            Config.Value.BooleanValue(false),
        )
    }

    @Test
    fun `when screen is started and nfc is enabled, no item is selected`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(true)

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
    fun `when screen is started and nfc is not enabled, no item is selected`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(false)

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
    fun `when screen is started and nfc is not enabled, read more button is not displayed`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(false)

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

            verify(navController).navigate(SelectDocumentDestinations.TypesOfPhotoID)
        }

    @Test
    fun `when nfc is enabled and yes is tapped, it is selected`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when nfc is enabled and no is tapped, it is selected`() {
        composeTestRule.setSelectDrivingLicenceContent()

        given(nfcChecker.hasNfc()).willReturn(true)

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
    fun `when nfc is not enabled and yes is tapped, it is selected`() {
        given(nfcChecker.hasNfc()).willReturn(false)

        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when nfc is not enabled and no is tapped, it is selected`() {
        composeTestRule.setSelectDrivingLicenceContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .performClick()
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when nfc is enabled and user has not made a choice, the confirm button is disabled`() {
        given(nfcChecker.hasNfc()).willReturn(true)

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
    fun `when  nfc is not enabled and user has not made a choice, the confirm button is disabled`() {
        given(nfcChecker.hasNfc()).willReturn(false)

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
    fun `when nfc is enabled and yes is selected, it navigates to confirm document`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(true)

            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(yesOption, useUnmergedTree = true)
                .performClick()

            composeTestRule
                .onNode(continueButton)
                .assertIsEnabled()
                .performClick()

            // DCMAW-8798: update SelectDocumentDestinations.ConfirmPassport
            // to SelectDocumentDestinations.ConfirmDrivingLicence
            verify(navController).navigate(SelectDocumentDestinations.ConfirmPassport)
        }

    @Test
    fun `when nfc is not enabled and yes is selected, it navigates to confirm document`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(false)

            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(yesOption, useUnmergedTree = true)
                .performClick()

            composeTestRule
                .onNode(continueButton)
                .assertIsEnabled()
                .performClick()
            // DCMAW-8798: update SelectDocumentDestinations.ConfirmPassport
            // to SelectDocumentDestinations.ConfirmDrivingLicence
            verify(navController).navigate(SelectDocumentDestinations.ConfirmPassport)
        }

    @Test
    fun `when nfc is enabled and no is selected, it navigates to Nfc Abort Confirmation`() =
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

            verify(navController).navigate(SelectDocumentDestinations.NfcAbortConfirmationScreen)
        }

    @Test
    fun `when nfc is not enabled and no is selected, it navigates to No Nfc Abort Confirmation`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(false)

            composeTestRule.setSelectDrivingLicenceContent()

            composeTestRule
                .onNode(noOption, useUnmergedTree = true)
                .assertIsEnabled()
                .performClick()

            composeTestRule
                .onNode(continueButton)
                .assertIsEnabled()
                .performClick()

            verify(navController).navigate(SelectDocumentDestinations.NoNfcAbortConfirmationScreen)
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
