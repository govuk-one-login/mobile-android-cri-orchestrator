package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
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
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

@RunWith(AndroidJUnit4::class)
class ContinueToProveYourIdentityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val configStore: ConfigStore = mock()
    private val stateFlow = MutableStateFlow<ProveYourIdentityState>(ProveYourIdentityState.Idle)
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

        whenever(viewModel.state).thenReturn(stateFlow)
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
    fun `when nfc is available navigate to passport journey`() {
        stateFlow.value = ProveYourIdentityState.NfcAvailable

        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        verify(navController).navigate(SelectDocumentDestinations.Passport)
    }

    @Test
    fun `when nfc is available navigate to driving licence journey`() {
        stateFlow.value = ProveYourIdentityState.NfcNotAvailable

        composeTestRule.setContent {
            ContinueToProveYourIdentityScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

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

        whenever(configStore.read(NfcConfigKey.StubNcfCheck)).thenReturn(
            flowOf(Config.Value.BooleanValue(false)),
        )

        composeTestRule
            .onNode(primaryButton)
            .performClick()

        verify(viewModel).onContinueClick()
    }
}
