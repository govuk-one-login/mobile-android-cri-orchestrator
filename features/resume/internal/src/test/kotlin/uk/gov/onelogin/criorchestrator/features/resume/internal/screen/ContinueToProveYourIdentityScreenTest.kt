package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.resume.internal.screen.ContinueToProveYourIdentityViewModel.ContinueToProveYourIdentityAction
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

@RunWith(AndroidJUnit4::class)
class ContinueToProveYourIdentityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val configStore: ConfigStore = mock()
    private val actions = MutableSharedFlow<ContinueToProveYourIdentityAction>()
    private lateinit var primaryButton: SemanticsMatcher
    private val viewModel =
        spy(
            ContinueToProveYourIdentityViewModel(
                analytics = mock<ResumeAnalytics>(),
                nfcChecker = mock(),
                configStore = configStore,
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        primaryButton =
            hasText(context.getString(R.string.continue_to_prove_your_identity_screen_button))
        whenever(viewModel.actions).thenReturn(actions)
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
    fun `when nfc is available, navigate to passport journey`() =
        runTest {
            composeTestRule.setContent {
                ContinueToProveYourIdentityScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }

            actions.emit(ContinueToProveYourIdentityAction.NavigateToPassport)

            verify(navController).navigate(SelectDocumentDestinations.Passport)
        }

    @Test
    fun `when nfc is not available, navigate to driving licence journey`() =
        runTest {
            composeTestRule.setContent {
                ContinueToProveYourIdentityScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }

            actions.emit(ContinueToProveYourIdentityAction.NavigateToDrivingLicense)

            verify(navController).navigate(SelectDocumentDestinations.DrivingLicence)
        }

    @Test
    fun `when continue button is clicked, it calls the view model`() {
        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composeTestRule
            .onNode(primaryButton)
            .performClick()

        verify(viewModel).onContinueClick()
    }
}
