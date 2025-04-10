package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class SelectBrpViewModelTest {
    private val analytics = mock<SelectDocAnalytics>()

    private val viewModel by lazy {
        SelectBrpViewModel(
            analytics = analytics,
        )
    }

    // DCMAW-10690 | AC1: Viewing biometric document screen
    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analytics).trackScreen(
            id = SelectDocScreenId.SelectBrp,
            title = SelectBrpConstants.titleId,
        )
    }

    // DCMAW-10690 | AC2: AC2: User has a biometric document (analytics)
    // DCMAW-10690 | AC3: User doesn’t have a biometric document (analytics)
    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when selection is confirmed, it sends analytics`(selection: Int) {
        viewModel.onContinueClicked(selection)

        val item =
            listOf(
                SelectBrpConstants.selectionItems[0],
                SelectBrpConstants.selectionItems[1],
            )[selection]

        verify(analytics).trackFormSubmission(
            buttonText = R.string.selectdocument_brp_continue_button,
            response = item,
        )
    }

    // DCMAW-10690 | AC2: AC2: User has a biometric document
    @Test
    fun `when biometric document is selected, navigate to confirmation`() {
        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(0)

                assertEquals(SelectBrpAction.NavigateToBrpConfirmation, awaitItem())
            }
        }
    }

    // DCMAW-10690 | AC3: User doesn’t have a biometric document
    @Test
    fun `when biometric document is not selected, navigate to driving licence`() {
        runTest {
            viewModel.actions.test {
                viewModel.onContinueClicked(1)

                assertEquals(SelectBrpAction.NavigateToDrivingLicence, awaitItem())
            }
        }
    }

    // DCMAW-10690 | AC8: User wants to learn about different document options (analytics)
    @Test
    fun `when the read more button is pressed, it sends analytics`() {
        viewModel.onReadMoreClicked()

        verify(analytics).trackButtonEvent(R.string.selectdocument_brp_read_more_button)
    }

    // DCMAW-10690 | AC8: User wants to learn about different document options
    @Test
    fun `when the read more button is pressed, the user sees the types of photo ID screen`() {
        runTest {
            viewModel.actions.test {
                viewModel.onReadMoreClicked()

                assertEquals(SelectBrpAction.NavigateToTypesOfPhotoID, awaitItem())
            }
        }
    }
}
