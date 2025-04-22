package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import org.mockito.Mockito.mock
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.analytics.IdCheckWrapperAnalytics
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.config.createTestInstance
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

fun SyncIdCheckViewModel.Companion.createTestInstance(
    enableManualLauncher: Boolean = false,
    session: Session = Session.createMobileAppMobileInstance(),
    biometricTokenResult: BiometricTokenResult =
        BiometricTokenResult.Success(
            BiometricToken.createTestToken(),
        ),
    launcherDataReader: LauncherDataReader =
        LauncherDataReader(
            FakeSessionStore(
                session = session,
            ),
            biometricTokenReader =
                StubBiometricTokenReader(
                    biometricTokenResult = biometricTokenResult,
                ),
        ),
    configStore: ConfigStore =
        FakeConfigStore(
            initialConfig =
                Config.createTestInstance(
                    enableManualLauncher = enableManualLauncher,
                ),
        ),
    logger: Logger = mock(),
    analytics: IdCheckWrapperAnalytics = mock(),
) = SyncIdCheckViewModel(
    configStore = configStore,
    launcherDataReader = launcherDataReader,
    logger = logger,
    analytics = analytics,
)
