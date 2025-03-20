package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension

@ExtendWith(MainDispatcherExtension::class)
class SelectPassportViewModelTest {
    private val analyticsLogger = mock<SelectDocumentAnalytics>()

    private val viewModel by lazy {
        SelectPassportViewModel(
            analytics =
            analyticsLogger,
        )
    }

    // DCMAW-8054 | AC1: Viewing passport screen
    @Test
    fun `when screen starts, it sends analytics`() {
        viewModel.onScreenStart()

        verify(analyticsLogger)
            .trackScreen(
                id = SelectDocumentScreenId.SelectPassport,
                title = R.string.selectdocument_passport_title,
            )
    }

    // DCMAW-8054 | AC2: User has a passport
    // DCMAW-8054 | AC3: User doesn’t have a passport
    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when selection is confirmed, it sends analytics`(selection: Int) {
        viewModel.onItemSelected(selection)
        viewModel.onConfirmSelection()

        val item =
            listOf(
                R.string.selectdocument_passport_selection_yes,
                R.string.selectdocument_passport_selection_no,
            )[selection]

        verify(analyticsLogger)
            .trackFormSubmission(
                R.string.selectdocument_passport_continuebutton,
                item,
            )
    }

    // DCMAW-8054 | AC4: User doesn’t select an option
    @Disabled("This AC has not yet been implemented")
    @Test
    fun `when selection is confirmed, it sends analytics`() {
        viewModel.onConfirmSelection()

        assertTrue(false)
    }

    @Test
    fun `when the read more button is pressed, it sends analytics`() {
        viewModel.readMoreButtonAction()

        verify(analyticsLogger)
            .trackButtonEvent(R.string.selectdocument_passport_readmore_button)
    }

    // DCMAW-8054 | AC8: User wants to learn about different document options
    @Disabled("This AC has not yet been implemented")
    @Test
    fun `when the read more button is pressed, the user sees the types of photo ID screen`() {
        viewModel.readMoreButtonAction()

        assertTrue(false)
    }

    @Test
    fun `when the screen loads, no item is selected`() {
        assertEquals(viewModel.selectedIndex, null)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `when item selected, selectedItem is updated`(selection: Int) {
        viewModel.onItemSelected(selection)

        assertEquals(viewModel.selectedIndex, selection)
    }
}
