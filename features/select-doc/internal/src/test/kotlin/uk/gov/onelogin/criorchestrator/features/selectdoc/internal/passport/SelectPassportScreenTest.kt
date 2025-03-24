package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

@RunWith(AndroidJUnit4::class)
class SelectPassportScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var readMoreButton: SemanticsMatcher
    private lateinit var yesOption: SemanticsMatcher
    private lateinit var noOption: SemanticsMatcher
    private lateinit var confirmButton: SemanticsMatcher

    private val navController: NavController = mock()
    private val stateFlow =
        MutableStateFlow(
            SelectPassportState(selection = PassportSelection.Unselected),
        )

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

        whenever(viewModel.state).thenReturn(stateFlow)
    }

    @Test
    fun `when screen is started, it calls the view model`() {
        setUpContent()
        verify(viewModel).onScreenStart()
    }

    @Test
    fun `when screen is started, no item is selected`() {
        setUpContent()
        swipeToAdditionalContent()

        verify(viewModel).onScreenStart()

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
    fun `when read more is selected, it calls the view model`() {
        setUpContent()
        composeTestRule
            .onNode(readMoreButton)
            .performClick()

        verify(viewModel).onReadMoreClick()
    }

    @Test
    fun `when yes is tapped, it is selected`() {
        setUpContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()
            .onSibling()
            .assertIsSelected()
    }

    @Test
    fun `when no is tapped, it is selected`() {
        setUpContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .performClick()
            .onSibling()
            .assertIsSelected()
    }

    // DCMAW-8054 | AC4: User doesn’t select an option
    @Test
    fun `when user has not made choice yet, the confirm button is disabled`() {
        setUpContent()
        composeTestRule
            .onNode(confirmButton)
            .performClick()

        composeTestRule
            .onNode(confirmButton)
            .assertIsNotEnabled()

        verify(viewModel, never()).onConfirmSelection(any())
    }

    @Test
    fun `when yes is selected and confirm is tapped, it calls the view model`() {
        setUpContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(viewModel).onConfirmSelection(0)
    }

    @Test
    fun `when no is selected and confirm is tapped, it calls the view model`() {
        setUpContent()
        swipeToAdditionalContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(viewModel).onConfirmSelection(1)
    }

    @Test
    fun `when passport is selected, navigate to confirmation screen`() {
        stateFlow.value =
            SelectPassportState(
                selection = PassportSelection.Selected,
            )

        setUpContent()

        verify(navController).navigate(SelectDocumentDestinations.Confirm)
    }

    @Test
    fun `when no passport is selected, navigate to BRP selection screen`() {
        stateFlow.value =
            SelectPassportState(
                selection = PassportSelection.NotSelected,
            )

        setUpContent()

        verify(navController).navigate(SelectDocumentDestinations.BRP)
    }

    private fun setUpContent() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
                navController = navController,
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
