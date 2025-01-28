package uk.gov.onelogin.criorchestrator.sdk.publicapi

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

class RememberCriOrchestratorKtTest {
    private val httpClient =
        StubHttpClient(
            apiResponse = ApiResponse.Offline,
        )

    private val analyticsLogger = FakeAnalyticsLogger()

    @Test
    fun `it returns a value`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                rememberCriOrchestrator(
                    authenticatedHttpClient = httpClient,
                    analyticsLogger = analyticsLogger,
                )
            }.test {
                assertInstanceOf<CriOrchestratorComponent>(awaitItem())
                cancel()
            }
        }
}
