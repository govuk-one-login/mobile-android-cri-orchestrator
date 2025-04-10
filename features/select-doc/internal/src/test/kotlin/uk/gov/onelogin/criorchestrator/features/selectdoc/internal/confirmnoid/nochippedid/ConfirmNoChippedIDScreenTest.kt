package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEnabled
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.verify
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations

@RunWith(AndroidJUnit4::class)
class ConfirmNoChippedIDScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var confirmButton: SemanticsMatcher

    private val navController: NavController = mock()
    private val analytics: SelectDocAnalytics = mock()

    private val viewModel =
        spy(
            ConfirmNoChippedIDViewModel(
                analytics = analytics,
            ),
        )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        confirmButton = hasText(context.getString(R.string.confirm_nochippedid_confirmbutton))

        composeTestRule.setContent {
            ConfirmNoChippedIDScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }

    @Test
    fun `when confirm is tapped, it navigates to Confirm Abort screen`() {
        composeTestRule
            .onNode(confirmButton)
            .assertIsEnabled()
            .performClick()

        verify(navController).navigate(SelectDocDestinations.ConfirmAbort)
    }
}
