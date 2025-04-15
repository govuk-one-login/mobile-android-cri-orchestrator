package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
class SyncIdCheckViewModelTest {
    private val documentVariety = DocumentVariety.NFC_PASSPORT
    private val biometricToken = BiometricToken.createTestToken()

    private fun viewModel(
        biometricTokenResult: BiometricTokenResult = BiometricTokenResult.Success(biometricToken),
        session: Session = this.session,
    ) = SyncIdCheckViewModel(
        logger = logger,
        launcherDataReader =
            LauncherDataReader(
                sessionStore =
                    FakeSessionStore(
                        session = session,
                    ),
                biometricTokenReader =
                    StubBiometricTokenReader(
                        biometricTokenResult = biometricTokenResult,
                    ),
            ),
    )

    private val logger = SystemLogger()
    private val session = Session.createTestInstance()
    val activityResultContractParameters =
        IdCheckSdkActivityResultContractParameters(
            stubExitState = ExitStateOption.None,
            logger = logger,
        )

    val manualLauncher = ManualLauncher()
    val launcherData =
        LauncherData.createTestInstance(
            session = session,
            biometricToken = biometricToken,
        )

    fun manualLauncherState(
        manualLauncher: ManualLauncher = this.manualLauncher,
        launcherData: LauncherData = this.launcherData,
        activityResultContractParameters: IdCheckSdkActivityResultContractParameters =
            this.activityResultContractParameters,
    ) = SyncIdCheckState.Display(
        manualLauncher = manualLauncher,
        launcherData = launcherData,
        activityResultContractParameters = activityResultContractParameters,
    )

    companion object {
        @JvmStatic
        fun provideSdkResultActionParams(): Stream<Arguments> =
            Stream.of<Arguments>(
                Arguments.of(
                    IdCheckSdkExitState.HappyPath,
                    Session.createDesktopAppDesktopInstance(),
                    SyncIdCheckAction.NavigateToReturnToDesktopWeb,
                ),
                Arguments.of(
                    IdCheckSdkExitState.HappyPath,
                    Session.createMobileAppMobileInstance(),
                    SyncIdCheckAction.NavigateToReturnToMobileWeb,
                ),
            )
    }

    @Test
    fun `before screen is started, starts loading`() =
        runTest {
            val viewModel = viewModel()
            viewModel.state.test {
                assertEquals(SyncIdCheckState.Loading, awaitItem())
                cancel()
            }
        }

    @Test
    fun `when screen is started, and get biometric token is successful, it loads the manual launcher`() =
        runTest {
            val viewModel = viewModel()
            viewModel.state.test {
                skipItems(1) // Loading
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(
                    manualLauncherState(),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `when stub exit state is selected, it updates the state`() =
        runTest {
            val viewModel = viewModel()
            viewModel.state.test {
                skipItems(1) // Loading
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(
                    manualLauncherState(
                        activityResultContractParameters =
                            activityResultContractParameters.copy(
                                stubExitState = ExitStateOption.None,
                            ),
                        manualLauncher =
                            manualLauncher.copy(
                                selectedExitState = 0,
                            ),
                    ),
                    awaitItem(),
                )

                viewModel.onStubExitStateSelected(1)

                assertEquals(
                    manualLauncherState(
                        activityResultContractParameters =
                            activityResultContractParameters.copy(
                                stubExitState = ExitStateOption.HappyPath,
                            ),
                        manualLauncher =
                            manualLauncher.copy(
                                selectedExitState = 1,
                            ),
                    ),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `when sdk launch request is received, it emits the launch action`() =
        runTest {
            val viewModel = viewModel()
            viewModel.actions.test {
                viewModel.onScreenStart(documentVariety = documentVariety)
                viewModel.onIdCheckSdkLaunchRequest(launcherData)

                assertEquals(
                    SyncIdCheckAction.LaunchIdCheckSdk(
                        launcherData = launcherData,
                        logger = logger,
                    ),
                    awaitItem(),
                )
            }
        }

    @ParameterizedTest(name = "{index} sdk result {0} with session {1} results in {2}")
    @MethodSource("provideSdkResultActionParams")
    fun `when sdk result is received, it emits the navigation action`(
        sdkResult: IdCheckSdkExitState,
        session: Session,
        expectedNavigationAction: SyncIdCheckAction,
    ) = runTest {
        val viewModel =
            viewModel(
                session = session,
            )
        viewModel.actions.test {
            viewModel.onScreenStart(documentVariety = documentVariety)
            viewModel.onIdCheckSdkResult(sdkResult)

            assertEquals(
                expectedNavigationAction,
                awaitItem(),
            )
        }
    }

    @Test
    fun `when get biometric token fails with unrecoverable error, it navigates to recoverable error`() =
        runTest {
            val viewModel =
                viewModel(
                    biometricTokenResult =
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
                viewModel(
                    BiometricTokenResult.Offline,
                )

            viewModel.actions.test {
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(SyncIdCheckAction.NavigateToRecoverableError, awaitItem())
            }
        }
}
