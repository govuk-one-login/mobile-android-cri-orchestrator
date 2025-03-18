package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics

@RunWith(AndroidJUnit4::class)
class ContinueToProveYourIdentityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val nfcChecker: NfcChecker = mock()
    private lateinit var primaryButton: SemanticsMatcher
    private val viewModel =
        spy(
            ContinueToProveYourIdentityViewModel(
                analytics = mock<ResumeAnalytics>(),
                nfcChecker = nfcChecker,
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        primaryButton =
            hasText(context.getString(R.string.continue_to_prove_your_identity_screen_button))
    }

    @Test
    fun `when screen started, it calls the view model`() {
        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
            )
        }

        verify(viewModel).onScreenStart()
    }

    fun `when continue clicked and nfc available`() {
        whenever(nfcChecker.hasNfc()).thenReturn(true)

        viewModel.onContinueClick()

        verify(viewModel).navigateToPassportJourney()
    }

    @Test
    fun `when continue clicked and nfc not available`() {
        whenever(nfcChecker.hasNfc()).thenReturn(false)

        viewModel.onContinueClick()

        verify(viewModel).navigateToDlJourney()
    }

    @Test
    fun `when continue button is clicked, it calls the view model`() {
        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
            )
        }

        composeTestRule
            .onNode(primaryButton)
            .performClick()

        verify(viewModel).onContinueClick()
    }
}
