package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.IdCheckSdkActivityResultContractParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.IdCheckWrapperAnalytics
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.IdCheckWrapperScreenId
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.testing.MainDispatcherExtension
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
class SyncIdCheckViewModelTest {
    private val documentVariety = DocumentVariety.NFC_PASSPORT
    private var enableManualLauncher = false
    private var session = Session.createTestInstance()
    private val biometricToken = BiometricToken.createTestToken()
    private val analytics = mock<IdCheckWrapperAnalytics>()
    private val sessionStore = FakeSessionStore(session)
    private val launcherData by lazy {
        LauncherData.createTestInstance(
            session = session.copyUpdateState { advanceAtLeastDocumentSelected() },
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
                    biometricTokenReader =
                        StubBiometricTokenReader(
                            BiometricTokenResult.Success(
                                biometricToken,
                            ),
                        ),
                    configStore = FakeConfigStore(),
                ),
            analytics = analytics,
            sessionStore = sessionStore,
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
        private const val REDIRECT_URI = "http://mam-redirect-uri"
        private val mamSession =
            Session.createTestInstance(
                redirectUri = REDIRECT_URI,
            )

        @JvmStatic
        fun provideSdkResultActionParams(): Stream<Arguments> {
            val unhappyPaths =
                ExitStateOption.entries
                    .filter {
                        it.exitState !is IdCheckSdkExitState.HappyPath
                    }.mapNotNull {
                        it.exitState
                    }.stream()
                    .flatMap { sdkResult ->
                        listOf(
                            Arguments.of(
                                sdkResult,
                                Session.createDesktopAppDesktopInstance(),
                                SyncIdCheckAction.NavigateToAbortedReturnToDesktopWeb,
                            ),
                            Arguments.of(
                                sdkResult,
                                mamSession,
                                SyncIdCheckAction.NavigateToAbortedRedirectToMobileWebHolder(
                                    redirectUri = REDIRECT_URI,
                                ),
                            ),
                        ).stream()
                    }
            val happyPaths =
                ExitStateOption.entries
                    .filter {
                        it.exitState is IdCheckSdkExitState.HappyPath
                    }.mapNotNull {
                        it.exitState
                    }.stream()
                    .flatMap { sdkResult ->
                        listOf(
                            Arguments.of(
                                sdkResult,
                                Session.createDesktopAppDesktopInstance(),
                                SyncIdCheckAction.NavigateToReturnToDesktopWeb,
                            ),
                            Arguments.of(
                                sdkResult,
                                Session.createMobileAppMobileInstance(),
                                SyncIdCheckAction.NavigateToReturnToMobileWeb,
                            ),
                        ).stream()
                    }

            return Stream.concat(happyPaths, unhappyPaths)
        }
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
        stubExitState: IdCheckSdkExitState,
        session: Session,
        expectedNavigationAction: SyncIdCheckAction,
    ) = runTest {
        this@SyncIdCheckViewModelTest.session = session
        viewModel.actions.test {
            viewModel.onScreenStart(documentVariety = documentVariety)
            viewModel.onIdCheckSdkResult(stubExitState)

            assertEquals(
                expectedNavigationAction,
                awaitItem(),
            )
        }
    }

    @Test
    fun `when screen is started, it sends analytics`() {
        val viewModel = viewModel()
        viewModel.onScreenStart(documentVariety = documentVariety)

        verify(analytics)
            .trackScreen(
                id = IdCheckWrapperScreenId.SyncIdCheckScreen,
                title = R.string.loading,
            )
    }

    @Test
    fun `when get biometric token fails with unrecoverable error, it navigates to unrecoverable error`() =
        runTest {
            val viewModel =
                viewModel(
                    biometricTokenResult =
                        BiometricTokenResult.Error(
                            Exception("Error"),
                        ),
                )

            viewModel.actions.test {
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(SyncIdCheckAction.NavigateToUnrecoverableError, awaitItem())
            }
        }

    @Test
    fun `when get biometric token fails with unrecoverable error, it navigates to recoverable error`() =
        runTest {
            val viewModel =
                viewModel(
                    biometricTokenResult = BiometricTokenResult.Offline,
                )

            viewModel.actions.test {
                viewModel.onScreenStart(documentVariety = documentVariety)

                assertEquals(SyncIdCheckAction.NavigateToRecoverableError, awaitItem())
            }
        }

    private fun viewModel(biometricTokenResult: BiometricTokenResult = BiometricTokenResult.Success(biometricToken)) =
        SyncIdCheckViewModel(
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
                    configStore = FakeConfigStore(),
                ),
            analytics = analytics,
            configStore =
                FakeConfigStore(
                    initialConfig =
                        Config.createTestInstance(
                            enableManualLauncher = enableManualLauncher,
                        ),
                ),
            sessionStore = sessionStore,
        )
}
