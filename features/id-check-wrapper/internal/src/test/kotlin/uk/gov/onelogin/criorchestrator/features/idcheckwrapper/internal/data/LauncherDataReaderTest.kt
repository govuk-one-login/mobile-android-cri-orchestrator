package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.backend.BackendMode
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.FakeConfig.ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey.IdCheckAsyncBackendBaseUrl
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createDesktopAppDesktopInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createMobileAppMobileInstance
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import kotlin.time.Duration.Companion.seconds

class LauncherDataReaderTest {
    private var initialConfig =
        Config(
            entries =
                persistentListOf(
                    Config.Entry<Config.Value.StringValue>(
                        key = IdCheckAsyncBackendBaseUrl,
                        value =
                            Config.Value.StringValue(
                                ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE,
                            ),
                    ),
                    Config.Entry<Config.Value.BooleanValue>(
                        key = SdkConfigKey.BypassIdCheckAsyncBackend,
                        value =
                            Config.Value.BooleanValue(false),
                    ),
                ),
        )
    private val configStore by lazy {
        FakeConfigStore(initialConfig)
    }

    private companion object {
        private val session = Session.createTestInstance()
        private val biometricToken = BiometricToken.createTestToken()
        private val documentVariety = DocumentVariety.NFC_PASSPORT
        private val expectedLauncherDataResult =
            LauncherDataReaderResult.Success(
                LauncherData(
                    session = session,
                    biometricToken = biometricToken,
                    documentType = DocumentType.NFC_PASSPORT,
                    backendMode = BackendMode.V2,
                ),
            )

        private val sessionStore =
            FakeSessionStore(
                session = session,
            )
    }

    private fun createLauncherDataReader(sessionStore: SessionStore = LauncherDataReaderTest.sessionStore) =
        LauncherDataReader(
            sessionStore = sessionStore,
            biometricTokenReader =
                StubBiometricTokenReader(
                    BiometricTokenResult.Success(
                        biometricToken,
                    ),
                ),
            configStore = configStore,
        )

    @Test
    fun `read gets the launcher data`() =
        runTest {
            val launcherDataReader = createLauncherDataReader()
            val launcherDataResult =
                launcherDataReader.read(
                    documentVariety = documentVariety,
                )
            assertEquals(
                expectedLauncherDataResult,
                launcherDataResult,
            )
        }

    @Test
    fun `given different document variety, read gets the launcher data`() =
        runTest {
            val launcherDataReader = createLauncherDataReader()
            val launcherDataResult =
                launcherDataReader.read(
                    documentVariety = DocumentVariety.BRP,
                )

            assertEquals(
                expectedLauncherDataResult.copy(
                    launcherData =
                        expectedLauncherDataResult.launcherData.copy(
                            documentType = DocumentType.BRP,
                        ),
                ),
                launcherDataResult,
            )
        }

    @Test
    fun `given different bypass ID Check backend is enabled, read gets the correct launcher data`() =
        runTest {
            initialConfig =
                Config(
                    entries =
                        persistentListOf(
                            Config.Entry<Config.Value.StringValue>(
                                key = IdCheckAsyncBackendBaseUrl,
                                value =
                                    Config.Value.StringValue(
                                        ID_CHECK_BACKEND_ASYNC_URL_TEST_VALUE,
                                    ),
                            ),
                            Config.Entry<Config.Value.BooleanValue>(
                                key = SdkConfigKey.BypassIdCheckAsyncBackend,
                                value =
                                    Config.Value.BooleanValue(true),
                            ),
                        ),
                )
            val launcherDataReader = createLauncherDataReader()
            val launcherDataResult =
                launcherDataReader.read(
                    documentVariety = documentVariety,
                )

            assertEquals(
                expectedLauncherDataResult.copy(
                    launcherData =
                        expectedLauncherDataResult.launcherData.copy(
                            backendMode = BackendMode.Bypass,
                        ),
                ),
                launcherDataResult,
            )
        }

    @Test
    fun `given mobile-app-mobile journey, read gets the launcher data`() =
        runTest {
            val launcherDataReader =
                createLauncherDataReader(
                    sessionStore =
                        FakeSessionStore(
                            Session.createMobileAppMobileInstance(),
                        ),
                )
            val launcherDataResult =
                launcherDataReader.read(
                    documentVariety = documentVariety,
                )

            val journeyType =
                (launcherDataResult as LauncherDataReaderResult.Success).launcherData.journeyType
            assertEquals(JourneyType.MOBILE_APP_MOBILE, journeyType)
        }

    @Test
    fun `given desktop-app-desktop journey, read gets the launcher data`() =
        runTest {
            val launcherDataReader =
                createLauncherDataReader(
                    sessionStore =
                        FakeSessionStore(
                            Session.createDesktopAppDesktopInstance(),
                        ),
                )
            val launcherDataResult =
                launcherDataReader.read(
                    documentVariety = documentVariety,
                )

            val journeyType =
                (launcherDataResult as LauncherDataReaderResult.Success).launcherData.journeyType
            assertEquals(JourneyType.DESKTOP_APP_DESKTOP, journeyType)
        }

    @Test
    fun `given session is initially null, it waits for a good value`() =
        runTest {
            val sessionStore = FakeSessionStore(null)
            val launcherDataReader =
                createLauncherDataReader(
                    sessionStore = sessionStore,
                )
            val asyncLauncherData =
                async {
                    launcherDataReader.read(documentVariety)
                }

            launch {
                sessionStore.write(session)
            }

            val launcherDataResult = asyncLauncherData.await()

            assertEquals(
                expectedLauncherDataResult,
                launcherDataResult,
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `if session is never loaded, it throws`() =
        runTest {
            val sessionStore = FakeSessionStore(null)
            val launcherDataReader =
                createLauncherDataReader(
                    sessionStore = sessionStore,
                )
            val asyncLauncherData =
                async {
                    launcherDataReader.read(documentVariety)
                }

            advanceTimeBy(11.seconds)

            assertInstanceOf<TimeoutCancellationException>(
                asyncLauncherData.getCompletionExceptionOrNull(),
            )
        }
}
