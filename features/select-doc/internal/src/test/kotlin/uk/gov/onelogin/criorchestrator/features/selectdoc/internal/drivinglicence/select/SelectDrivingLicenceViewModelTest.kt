package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

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
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class SelectDrivingLicenceViewModelTest {
    private val configStore: ConfigStore = mock()
    private val analyticsLogger: SelectDocAnalytics = mock()
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
        whenever(configStore.readSingle(NfcConfigKey.NfcAvailability)).thenReturn(
            Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
        )
        given(nfcChecker.hasNfc()).willReturn(false)
    }

    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocScreenId.SelectDrivingLicence,
                title = R.string.selectdocument_drivinglicence_title,
            )
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when continue is clicked and nfc is enabled, it sends analytics`(selection: Int) {
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
    fun `when continue is clicked and nfc is not enabled, it sends analytics`(selection: Int) {
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
    fun `when yes is selected and nfc is not enabled, navigate to confirmation`() {
        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(0)

                assertEquals(SelectDrivingLicenceAction.NavigateToConfirmation, awaitItem())
            }
        }
    }

    @Test
    fun `when no is not selected and nfc is enabled, navigate to confirm no chipped ID`() {
        given(nfcChecker.hasNfc()).willReturn(true)

        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(1)

                assertEquals(SelectDrivingLicenceAction.NavigateToConfirmNoChippedID, awaitItem())
            }
        }
    }

    @Test
    fun `when nfc is not enabled and no is not selected, navigate to confirm no non chipped ID`() {
        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(1)

                assertEquals(SelectDrivingLicenceAction.NavigateToConfirmNoNonChippedID, awaitItem())
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
