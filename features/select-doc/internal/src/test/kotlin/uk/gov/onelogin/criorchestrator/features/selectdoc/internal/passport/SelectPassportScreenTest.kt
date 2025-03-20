package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Ignore
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
        yesOption = hasContentDescription(context.getString(R.string.selectdocument_passport_selection_yes))
        noOption = hasContentDescription(context.getString(R.string.selectdocument_passport_selection_no))
        confirmButton = hasText(context.getString(R.string.selectdocument_passport_continuebutton))
    }

    @Test
    fun `when screen started, it calls the view model`() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
            )
        }

        verify(viewModel).onScreenStart()
    }

    @Test
    fun `when read more is selected, it calls the view model`() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
            )
        }

        composeTestRule
            .onNode(readMoreButton)
            .performClick()

        verify(viewModel).readMoreButtonAction()
    }

    @Ignore("Radio buttons are not appearing in node hierarchy - further investigation required")
    @Test
    fun `selected item is present in the view`() {
        // `@ParameterizedTest` is not supported in JUnit 4
        (0..1).forEach { selectedItem ->
            viewModel.onItemSelected(selectedItem)

            composeTestRule.setContent {
                SelectPassportScreen(
                    viewModel = viewModel,
                )
            }

            val expectedSelectedComponent = listOf(
                yesOption, noOption
            )[selectedItem]

            composeTestRule
                .onNode(expectedSelectedComponent)
                .assertIsSelected()
        }
    }

    @Ignore("Radio buttons are not appearing in node hierarchy - further investigation required")
    @Test
    fun `when yes is selected, it calls the view model`() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
            )
        }

        composeTestRule
            .onNode(yesOption)
            .performClick()

        verify(viewModel).onItemSelected(0)
    }

    @Ignore("Radio buttons are not appearing in node hierarchy - further investigation required")
    @Test
    fun `when no is selected, it calls the view model`() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
            )
        }

        composeTestRule
            .onNode(noOption)
            .performClick()

        verify(viewModel).onItemSelected(1)
    }

    @Test
    fun `when confirm is selected, it calls the view model`() {
        composeTestRule.setContent {
            SelectPassportScreen(
                viewModel = viewModel,
            )
        }

        composeTestRule
            .onNode(confirmButton)
            .performClick()

        verify(viewModel).onConfirmSelection()
    }
}