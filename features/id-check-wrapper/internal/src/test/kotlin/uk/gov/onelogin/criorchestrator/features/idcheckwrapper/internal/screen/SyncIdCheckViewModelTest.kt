package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class SyncIdCheckViewModelTest {
    private val session = Session.createTestInstance()
    private val documentVariety = DocumentVariety.NFC_PASSPORT
    private val biometricToken = BiometricToken.createTestToken()

    @Test
    fun `before screen is started, starts loading`() =
        runTest {
            val viewModel =
                createSyncIdCheckViewModel(
                    biometricTokenResult = BiometricTokenResult.Success(biometricToken),
                )

            viewModel.state.test {
                assertEquals(SyncIdCheckState.Loading, awaitItem())
                cancel()
            }
        }

    @Test
    fun `when screen is started, and get biometric token is successful, it loads the manual launcher`() =
        runTest {
            val viewModel =
                createSyncIdCheckViewModel(
                    biometricTokenResult = BiometricTokenResult.Success(biometricToken),
                )

            viewModel.state.test {
                skipItems(1) // Loading
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(
                    SyncIdCheckState.DisplayManualLauncher(
                        launcherData =
                            LauncherData(
                                documentType = DocumentType.NFC_PASSPORT,
                                journeyType = JourneyType.DESKTOP_APP_DESKTOP,
                                sessionId = session.sessionId,
                                biometricToken =
                                    BiometricToken(
                                        accessToken = biometricToken.accessToken,
                                        opaqueId = biometricToken.opaqueId,
                                    ),
                            ),
                    ),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `when get biometric token fails with unrecoverable error, it navigates to recoverable error`() =
        runTest {
            val viewModel =
                createSyncIdCheckViewModel(
                    BiometricTokenResult.Error(
                        "Error",
                        Exception("Error"),
                    ),
                )

            viewModel.actions.test {
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(SyncIdCheckAction.NavigateToUnRecoverableError, awaitItem())
            }
        }

    @Test
    fun `when get biometric token fails with recoverable error, it navigates to recoverable error`() =
        runTest {
            val viewModel =
                createSyncIdCheckViewModel(
                    BiometricTokenResult.Offline,
                )

            viewModel.actions.test {
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(SyncIdCheckAction.NavigateToRecoverableError, awaitItem())
            }
        }

    private fun createSyncIdCheckViewModel(biometricTokenResult: BiometricTokenResult): SyncIdCheckViewModel =
        SyncIdCheckViewModel(
            launcherDataReader =
                LauncherDataReader(
                    sessionStore =
                        FakeSessionStore(
                            session = session,
                        ),
                    biometricTokenReader = StubBiometricTokenReader(biometricTokenResult),
                ),
        )
}
