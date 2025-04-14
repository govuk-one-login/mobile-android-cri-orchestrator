package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

@RunWith(AndroidJUnit4::class)
class SyncIdCheckScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController: NavController = mock()
    private val actionsFlow = MutableSharedFlow<SyncIdCheckAction>()
    private val viewModel =
        mock<SyncIdCheckViewModel>().apply {
            whenever(actions).thenReturn(actionsFlow.asSharedFlow())
            whenever(state).thenReturn(MutableStateFlow(SyncIdCheckState.Loading))
        }

    @Before
    fun setup() {
        composeTestRule.setContent {
            SyncIdCheckScreen(
                documentVariety = DocumentVariety.NFC_PASSPORT,
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    // AC2: Loading state
    @Test
    fun `when screen is started, loading progress indicator is displayed`() {
        composeTestRule
            .onNode(hasText("Loading"))
            .assertIsDisplayed()
    }

    // AC4: User receives recoverable error on call
    @Test
    fun `when data launcher returns recoverable error, navigate to recoverable error screen`() =
        runTest {
            actionsFlow.emit(SyncIdCheckAction.NavigateToRecoverableError)
            composeTestRule.waitForIdle()

            verify(navController).navigate(ErrorDestinations.RecoverableError)
        }

    // AC3: User receives unrecoverable error on call
    @Test
    fun `when data launcher returns unrecoverable error, navigate to unrecoverable error screen`() =
        runTest {
            actionsFlow.emit(SyncIdCheckAction.NavigateToUnRecoverableError)
            composeTestRule.waitForIdle()

            verify(navController).navigate(HandbackDestinations.UnrecoverableError)
        }
}
