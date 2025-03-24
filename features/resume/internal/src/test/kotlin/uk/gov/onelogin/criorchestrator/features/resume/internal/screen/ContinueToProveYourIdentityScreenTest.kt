package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

@RunWith(AndroidJUnit4::class)
class ContinueToProveYourIdentityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val configStore: ConfigStore = mock()
    private lateinit var primaryButton: SemanticsMatcher
    private val nfcChecker: NfcChecker = mock()
    private val viewModel =
        spy(
            ContinueToProveYourIdentityViewModel(
                analytics = mock<ResumeAnalytics>(),
                nfcChecker = nfcChecker,
                configStore = configStore,
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        primaryButton =
            hasText(context.getString(R.string.continue_to_prove_your_identity_screen_button))
        given(nfcChecker.hasNfc()).willReturn(true)
        whenever(configStore.readSingle(NfcConfigKey.StubNcfCheck)).thenReturn(
            Config.Value.BooleanValue(false),
        )
    }

    @Test
    fun `when screen started, it calls the view model`() {
        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        verify(viewModel).onScreenStart()
    }

    @Test
    fun `given nfc is available, when click continue, navigate to passport journey`() {
        whenever(nfcChecker.hasNfc()).thenReturn(true)

        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composeTestRule
            .onNode(primaryButton)
            .performClick()

        verify(navController).navigate(SelectDocumentDestinations.Passport)
    }

    @Test
    fun `given nfc is not available, when click continue, navigate to driving licence journey`() {
        whenever(nfcChecker.hasNfc()).thenReturn(false)

        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composeTestRule
            .onNode(primaryButton)
            .performClick()

        verify(navController).navigate(SelectDocumentDestinations.DrivingLicence)
    }
}
