package uk.gov.onelogin.criorchestrator.sdk.publicapi

import android.app.Activity
import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import org.mockito.kotlin.mock
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.testing.assertions.assertExceptionEquals
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

class RememberCriOrchestratorKtTest {
    private val initialConfig =
        Config(
            entries =
                persistentListOf(
                    Config.Entry<Config.Value.StringValue>(
                        key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                        value = Config.Value.StringValue("baseurl"),
                    ),
                ),
        )
    private val criOrchestratorSdk =
        CriOrchestratorSdk.create(
            authenticatedHttpClient = StubHttpClient(apiResponse = ApiResponse.Offline),
            analyticsLogger = mock<AnalyticsLogger>(),
            initialConfig = initialConfig,
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
                assertInstanceOf<CriOrchestratorGraph>(awaitItem())
                cancel()
            }
        }

    @Test
    fun `given activity is null, it throws`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                withContext(
                    activity = null,
                ) {
                    rememberCriOrchestrator(
                        criOrchestratorSdk = criOrchestratorSdk,
                    )
                }
            }.test {
                assertExceptionEquals(IllegalStateException("No activity found"), awaitError())
                cancel()
            }
        }

    @Composable
    fun <T> withContext(
        activity: Activity? = mock<Activity>(),
        context: Context = activity ?: mock<Context>(),
        composable: @Composable () -> T,
    ): T {
        var result: T? = null
        CompositionLocalProvider(
            LocalActivity provides activity,
        ) {
            CompositionLocalProvider(
                LocalContext provides context,
            ) {
                result = composable()
            }
        }
        return result!!
    }
}
