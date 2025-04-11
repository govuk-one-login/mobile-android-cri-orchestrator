package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenProvider
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckAction
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import kotlin.test.assertEquals

@ExtendWith(MainDispatcherExtension::class)
class SyncIdCheckViewModelTest {
    private val biometricTokenProvider = mock<BiometricTokenProvider>()

    private val viewModel by lazy {
        SyncIdCheckViewModel(
            sessionStore = mock<SessionStore>(),
            biometricTokenProvider = biometricTokenProvider,
        )
    }

    @Test
    fun `when get token returns unrecoverable error, navigate to unrecoverable error`() =
        runTest {
            whenever(biometricTokenProvider.getBiometricToken(any(), any())).thenReturn(
                flowOf(BiometricTokenResult.Error("")),
            )

            viewModel.actions.test {
                viewModel.getBiometricToken("")

                assertEquals(SyncIdCheckAction.NavigateToUnRecoverableError, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when get token returns recoverable error, navigate to recoverable error`() =
        runTest {

        }

    @Test
    fun `when get token returns success, start id check sdk`() =
        runTest {

        }

    @Test
    fun `when get token returns loading, show loading`() =
        runTest {

        }

    @Test
    fun `when get token returns offline, show offline`() =
        runTest {

        }
}
