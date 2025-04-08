package uk.gov.onelogin.criorchestrator.sdk.publicapi

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import org.mockito.kotlin.mock
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

class RememberCriOrchestratorKtTest {
    private val criOrchestratorSdk =
        CriOrchestratorSdk.create(
            authenticatedHttpClient = StubHttpClient(apiResponse = ApiResponse.Offline),
            analyticsLogger = mock<AnalyticsLogger>(),
            initialConfig = Config(),
            logger = SystemLogger(),
            applicationContext = mock(),
        )

    @Test
    fun `it returns a value`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                withContext {
                    rememberCriOrchestrator(
                        criOrchestratorSdk = criOrchestratorSdk,
                    )
                }
            }.test {
                assertInstanceOf<CriOrchestratorComponent>(awaitItem())
                cancel()
            }
        }

    @Composable
    fun <T> withContext(
        context: Context = mock<Context>(),
        composable: @Composable () -> T,
    ): T {
        var result: T? = null
        CompositionLocalProvider(
            LocalContext provides context,
        ) {
            result = composable()
        }
        return result!!
    }
}
