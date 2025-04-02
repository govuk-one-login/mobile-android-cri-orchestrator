package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class SelectDrivingLicenceViewModelTest {
    private val configStore: ConfigStore = mock()
    private val analyticsLogger: SelectDocumentAnalytics = mock()
    private val nfcChecker = mock<NfcChecker>()

    private val viewModel by lazy {
        SelectDrivingLicenceViewModel(
            analytics = analyticsLogger,
            nfcChecker = nfcChecker,
            configStore = configStore,
        )
    }

    @BeforeEach
    fun setUp() {
        whenever(configStore.readSingle(NfcConfigKey.StubNcfCheck)).thenReturn(
            Config.Value.BooleanValue(false),
        )
    }

    @Test
    fun `when nfc is enabled and screen starts, it sends analytics`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocumentScreenId.SelectDrivingLicence,
                title = R.string.selectdocument_drivinglicence_title,
            )
    }

    @Test
    fun `when nfc is not enabled and screen starts, it sends analytics`() {
        given(nfcChecker.hasNfc()).willReturn(false)

        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocumentScreenId.SelectDrivingLicence,
                title = R.string.selectdocument_drivinglicence_title,
            )
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when nfc is enabled and continue is clicked, it sends analytics`(selection: Int) {
        given(nfcChecker.hasNfc()).willReturn(true)

        viewModel.onContinueClicked(selection)

        val item =
            listOf(
                R.string.selectdocument_drivinglicence_selection_yes,
                R.string.selectdocument_drivinglicence_selection_no,
            )[selection]

        verify(analyticsLogger)
            .trackFormSubmission(
                R.string.selectdocument_drivinglicence_continuebutton,
                item,
            )
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when nfc is not enabled and continue is clicked, it sends analytics`(selection: Int) {
        given(nfcChecker.hasNfc()).willReturn(false)

        viewModel.onContinueClicked(selection)

        val item =
            listOf(
                R.string.selectdocument_drivinglicence_selection_yes,
                R.string.selectdocument_drivinglicence_selection_no,
            )[selection]

        verify(analyticsLogger)
            .trackFormSubmission(
                R.string.selectdocument_drivinglicence_continuebutton,
                item,
            )
    }

    @Test
    fun `when nfc is enabled and yes is selected, navigate to confirmation`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(0)

                assertEquals(SelectDrivingLicenceAction.NavigateToConfirmation, awaitItem())
            }
        }
    }

    @Test
    fun `when nfc is not enabled and yes is selected, navigate to confirmation`() {
        given(nfcChecker.hasNfc()).willReturn(false)

        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(0)

                assertEquals(SelectDrivingLicenceAction.NavigateToConfirmation, awaitItem())
            }
        }
    }

    @Test
    fun `when nfc is  enabled and no is not selected, navigate to no nfc Abort`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(1)

                assertEquals(SelectDrivingLicenceAction.NavigateToNfcAbort, awaitItem())
            }
        }
    }

    @Test
    fun `when nfc is not enabled and no is not selected, navigate to no nfc Abort`() {
        given(nfcChecker.hasNfc()).willReturn(false)

        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(1)

                assertEquals(SelectDrivingLicenceAction.NavigateToNoNfcAbort, awaitItem())
            }
        }
    }

    @Test
    fun `when the read more button is pressed, it sends analytics`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        runTest {
            viewModel.onReadMoreClick()

            verify(analyticsLogger)
                .trackButtonEvent(R.string.selectdocument_drivinglicence_readmore_button)
        }
    }

    @Test
    fun `when the read more button is pressed, the user sees the types of photo ID screen`() {
        runTest {
            given(nfcChecker.hasNfc()).willReturn(true)

            viewModel.actions.test {
                viewModel.onReadMoreClick()

                assertEquals(SelectDrivingLicenceAction.NavigateToTypesOfPhotoID, awaitItem())
            }
        }
    }
}
