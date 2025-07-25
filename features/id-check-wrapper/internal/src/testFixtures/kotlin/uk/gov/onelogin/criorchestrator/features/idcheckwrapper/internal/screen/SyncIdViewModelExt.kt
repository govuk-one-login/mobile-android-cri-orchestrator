package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.lifecycle.SavedStateHandle
import org.mockito.Mockito.mock
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate.InMemoryIdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore

fun SyncIdCheckViewModel.Companion.createTestInstance(
    sessionStore: SessionStore = FakeSessionStore(),
    configStore: ConfigStore =
        FakeConfigStore(
            initialConfig =
                Config.createTestInstance(
                    enableManualLauncher = false,
                ),
        ),
    launcherDataReader: LauncherDataReader =
        LauncherDataReader(
            sessionStore = sessionStore,
            biometricTokenReader =
                StubBiometricTokenReader(
                    biometricTokenResult =
                        BiometricTokenResult.Success(
                            BiometricToken.createTestToken(),
                        ),
                ),
            configStore = configStore,
        ),
    logger: Logger = mock(),
    idCheckSdkActiveStateStore: IdCheckSdkActiveStateStore = InMemoryIdCheckSdkActiveStateStore(logger),
) = SyncIdCheckViewModel(
    configStore = configStore,
    launcherDataReader = launcherDataReader,
    logger = logger,
    analytics = mock(),
    idCheckSdkActiveStateStore = idCheckSdkActiveStateStore,
    sessionStore = sessionStore,
    savedStateHandle =
        SavedStateHandle(
            mapOf(SyncIdCheckViewModel.SDK_HAS_DISPLAYED to false),
        ),
)
