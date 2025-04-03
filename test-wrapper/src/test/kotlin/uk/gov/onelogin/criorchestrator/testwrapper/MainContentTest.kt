package uk.gov.onelogin.criorchestrator.testwrapper

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.network.createStubHttpClient

@RunWith(AndroidJUnit4::class)
class MainContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    val criOrchestratorSdk =
        CriOrchestratorSdk.create(
            authenticatedHttpClient = createStubHttpClient(),
            analyticsLogger = mock<AnalyticsLogger>(),
            initialConfig =
                TestWrapperConfig.provideConfig(
                    resources = context.resources,
                ),
            logger = mock<Logger>(),
            applicationContext = context,
        )

    @Test
    fun `when click developer settings, it opens the dev menu`() {
        composeTestRule
            .setMainContent()

        composeTestRule
            .onNodeWithText("Developer settings", useUnmergedTree = true)
            .performClick()

        // Check that at least one of the dev menu items is displayed
        // to verify that the dev menu is open
        composeTestRule
            .onNodeWithText(SdkConfigKey.IdCheckAsyncBackendBaseUrl.name)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.setMainContent() =
        setContent {
            GdsTheme {
                MainContent(
                    criOrchestratorSdk = criOrchestratorSdk,
                    onSubUpdateRequest = {},
                )
            }
        }
}
