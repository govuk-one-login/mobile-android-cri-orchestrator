import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.ManualLauncher
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckAction
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckState
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen.SyncIdCheckViewModel
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
class SyncIdCheckViewModelTest {
    private val documentVariety = DocumentVariety.NFC_PASSPORT
    private var enableManualLauncher = false
    private var session = Session.createTestInstance()
    private val launcherData by lazy {
        LauncherData.createTestInstance(
            session = session,
        )
    }

    private val viewModel by lazy {
        SyncIdCheckViewModel(
            configStore =
                FakeConfigStore(
                    initialConfig =
                        Config.createTestInstance(
                            enableManualLauncher = enableManualLauncher,
                        ),
                ),
            logger = logger,
            launcherDataReader =
                LauncherDataReader(
                    sessionStore =
                        FakeSessionStore(
                            session = session,
                        ),
                ),
        )
    }

    private val logger = SystemLogger()
    private val activityResultContractParameters =
        IdCheckSdkActivityResultContractParameters(
            stubExitState = ExitStateOption.None,
            logger = logger,
        )

    private val manualLauncher = ManualLauncher()

    private fun manualLauncherState(
        manualLauncher: ManualLauncher = this.manualLauncher,
        launcherData: LauncherData = this.launcherData,
        activityResultContractParameters: IdCheckSdkActivityResultContractParameters =
            this.activityResultContractParameters,
    ) = SyncIdCheckState.Display(
        manualLauncher = manualLauncher,
        launcherData = launcherData,
        activityResultContractParameters = activityResultContractParameters,
    )

    private fun automaticLauncherState(
        launcherData: LauncherData = this.launcherData,
        activityResultContractParameters: IdCheckSdkActivityResultContractParameters =
            this.activityResultContractParameters,
    ) = SyncIdCheckState.Display(
        manualLauncher = null,
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
            viewModel.state.test {
                assertEquals(SyncIdCheckState.Loading, awaitItem())
                cancel()
            }
        }

    @Test
    fun `given manual launcher enabled, when screen is started, it loads the manual launcher`() =
        runTest {
            enableManualLauncher = true
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
    fun `given manual launcher isn't enabled, when screen is started, it loads the automatic launcher`() =
        runTest {
            enableManualLauncher = false
            viewModel.state.test {
                skipItems(1) // Loading
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(
                    automaticLauncherState(),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `given manual launcher is enabled, when stub exit state is selected, it updates the state`() =
        runTest {
            enableManualLauncher = true
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
    fun `given manual launcher isn't enabled, when stub exit state is selected, it throws`() =
        runTest {
            enableManualLauncher = false
            viewModel.state.test {
                skipItems(1) // Loading
                viewModel.onScreenStart(documentVariety = documentVariety)

                skipItems(1) // Automatic launcher

                assertThrows<IllegalArgumentException> {
                    viewModel.onStubExitStateSelected(1)
                }
            }
        }

    @Test
    fun `when sdk launch request is received, it emits the launch action`() =
        runTest {
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
        this@SyncIdCheckViewModelTest.session = session
        viewModel.actions.test {
            viewModel.onScreenStart(documentVariety = documentVariety)
            viewModel.onIdCheckSdkResult(sdkResult)

            assertEquals(
                expectedNavigationAction,
                awaitItem(),
            )
        }
    }
}
