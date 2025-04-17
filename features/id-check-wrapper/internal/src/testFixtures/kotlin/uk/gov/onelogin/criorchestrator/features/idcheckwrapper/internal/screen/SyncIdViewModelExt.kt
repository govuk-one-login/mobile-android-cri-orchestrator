package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

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
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore

fun SyncIdCheckViewModel.Companion.createTestInstance(
    configStore: ConfigStore =
        FakeConfigStore(
            initialConfig =
                Config.createTestInstance(
                    enableManualLauncher = false,
                ),
        ),
    launcherDataReader: LauncherDataReader =
        LauncherDataReader(
            sessionStore = FakeSessionStore(),
            biometricTokenReader =
                StubBiometricTokenReader(
                    biometricTokenResult =
                        BiometricTokenResult.Success(
                            BiometricToken.createTestToken(),
                        ),
                ),
        ),
    logger: Logger = mock(),
) = SyncIdCheckViewModel(
    configStore = configStore,
    launcherDataReader = launcherDataReader,
    logger = logger,
    analytics = mock(),
)
