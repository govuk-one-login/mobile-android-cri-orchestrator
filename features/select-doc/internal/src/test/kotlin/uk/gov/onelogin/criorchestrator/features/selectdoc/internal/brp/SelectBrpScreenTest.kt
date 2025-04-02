package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

@RunWith(AndroidJUnit4::class)
class SelectBrpScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var yesOption: SemanticsMatcher
    private lateinit var noOption: SemanticsMatcher
    private lateinit var continueButton: SemanticsMatcher
    private lateinit var readMore: SemanticsMatcher

    private val navController: NavController = mock()
    private val viewModel = spy(SelectBrpViewModel(analytics = mock()))

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        yesOption = hasText(context.getString(SelectBrpConstants.selectionItems[0]))
        noOption = hasText(context.getString(SelectBrpConstants.selectionItems[1]))
        continueButton = hasText(context.getString(SelectBrpConstants.continueButtonTextId))
        readMore = hasText(context.getString(SelectBrpConstants.readMoreButtonTextId))

        composeTestRule.setContent {
            SelectBrpScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    // DCMAW-10690 | AC8: User wants to learn about different document options
    @Test
    fun `when read more is selected, it navigates to types of ID`() {
        composeTestRule
            .onNode(readMore)
            .performClick()

        verify(navController).navigate(SelectDocumentDestinations.TypesOfPhotoID)
    }

    // DCMAW-10690 | AC4: User doesn’t select an option
    @Test
    fun `when screen is started, no item is selected`() {
        composeTestRule
            .onNode(continueButton)
            .performClick()

        composeTestRule
            .onNode(continueButton)
            .assertIsNotEnabled()

        verifyNoInteractions(navController)
    }

    // DCMAW-10690 | AC2: AC2: User has a biometric document
    @Test
    fun `when yes is selected, navigate to brp confirmation`() {
        swipeToAdditionalContent()

        composeTestRule
            .onNode(yesOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(continueButton, useUnmergedTree = true)
            .performClick()

        verify(navController).navigate(SelectDocumentDestinations.ConfirmBrp)
    }

    // DCMAW-10690 | AC3: User doesn’t have a biometric document
    @Test
    fun `when no is selected, navigate to Driving Licence`() {
        swipeToAdditionalContent()

        composeTestRule
            .onNode(noOption, useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNode(continueButton, useUnmergedTree = true)
            .performClick()

        verify(navController).navigate(SelectDocumentDestinations.DrivingLicence)
    }

    private fun swipeToAdditionalContent() {
        composeTestRule
            .onNode(
                readMore,
            ).onParent()
            .performTouchInput {
                swipeUp()
            }
    }
}
