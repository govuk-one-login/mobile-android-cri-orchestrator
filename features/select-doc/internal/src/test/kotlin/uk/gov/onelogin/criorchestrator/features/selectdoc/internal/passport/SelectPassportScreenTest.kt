package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

@RunWith(AndroidJUnit4::class)
class SelectPassportScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var readMoreButton: SemanticsMatcher
    private lateinit var yesOption: SemanticsMatcher
    private lateinit var noOption: SemanticsMatcher
    private lateinit var confirmButton: SemanticsMatcher

    private val viewModel =
        spy(
            SelectPassportViewModel(
                analytics = mock(),
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        readMoreButton = hasText(context.getString(R.string.selectdocument_passport_readmore_button))
        yesOption = hasText(context.getString(R.string.selectdocument_passport_selection_yes))
        noOption = hasText(context.getString(R.string.selectdocument_passport_selection_no))
        confirmButton = hasText(context.getString(R.string.selectdocument_passport_continuebutton))
    }

    @Test
    fun `when screen started, it calls the view model`() {
        setupContent()

        verify(viewModel).onScreenStart()
    }

    @Test
    fun `when read more is selected, it calls the view model`() {
        setupContent()

        composeTestRule
            .onNode(readMoreButton)
            .performClick()

        verify(viewModel).onReadMoreClick()
    }

    @Test
    fun `when view model selected item is yes, it is selected in the view`() {
        viewModel.onItemSelected(0)
        setupContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when view model selected item is no, it is selected in the view`() {
        viewModel.onItemSelected(1)
        setupContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when yes is selected, it calls the view model`() {
        setupContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()

        verify(viewModel).onItemSelected(0)
    }

    @Test
    fun `when no is selected, it calls the view model`() {
        setupContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .performClick()

        verify(viewModel).onItemSelected(1)
    }

    @Test
    fun `when confirm is selected, it calls the view model`() {
        setupContent()

        composeTestRule
            .onNode(confirmButton)
            .performClick()

        verify(viewModel).onConfirmSelection()
    }

    private fun setupContent() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
            )
        }
    }

    private fun swipeToAdditionalContent() {
        composeTestRule
            .onNode(
                readMoreButton,
            ).onParent()
            .performTouchInput {
                swipeUp()
            }
    }
}
