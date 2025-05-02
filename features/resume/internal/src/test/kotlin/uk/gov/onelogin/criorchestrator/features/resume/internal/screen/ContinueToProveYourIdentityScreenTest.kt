package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations

@RunWith(AndroidJUnit4::class)
class ContinueToProveYourIdentityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private lateinit var primaryButton: SemanticsMatcher
    private val nfcChecker = mock<NfcChecker>()
    private val viewModel =
        ContinueToProveYourIdentityViewModel(
            analytics = mock<ResumeAnalytics>(),
            nfcChecker = nfcChecker,
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        primaryButton =
            hasText(context.getString(R.string.continue_to_prove_your_identity_screen_button))
    }

    @Test
    fun `given nfc is available, when click continue, navigate to passport journey`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(true)
            composeTestRule.setContent {
                ContinueToProveYourIdentityScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }

            composeTestRule
                .onNode(primaryButton)
                .performClick()

            verify(navController).navigate(SelectDocDestinations.Passport)
        }

    @Test
    fun `given nfc is not available, when click continue, navigate to driving licence journey`() =
        runTest {
            given(nfcChecker.hasNfc()).willReturn(false)
            composeTestRule.setContent {
                ContinueToProveYourIdentityScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }

            composeTestRule
                .onNode(primaryButton)
                .performClick()

            verify(navController).navigate(SelectDocDestinations.DrivingLicence)
        }
}
