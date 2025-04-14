package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data

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
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.BiometricTokenResult
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.StubBiometricTokenReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.createTestToken
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import kotlin.time.Duration.Companion.seconds

class LauncherDataReaderTest {
    private companion object {
        private val session = Session.createTestInstance()
        private val biometricToken = BiometricToken.createTestToken()
        private val documentVariety = DocumentVariety.NFC_PASSPORT
        private val expectedLauncherDataResult =
            LauncherDataReaderResult.Success(
                LauncherData(
                    sessionId = session.sessionId,
                    journeyType = JourneyType.DESKTOP_APP_DESKTOP,
                    biometricToken = biometricToken,
                    documentType = DocumentType.NFC_PASSPORT,
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
    fun `given redirect URI is present, read gets the launcher data`() =
        runTest {
            val launcherDataReader =
                createLauncherDataReader(
                    sessionStore =
                        FakeSessionStore(
                            session.copy(
                                redirectUri = "https://example.com",
                            ),
                        ),
                )
            val launcherDataResult =
                launcherDataReader.read(
                    documentVariety = documentVariety,
                )
            assertEquals(
                expectedLauncherDataResult.copy(
                    launcherData =
                        expectedLauncherDataResult.launcherData.copy(
                            journeyType = JourneyType.MOBILE_APP_MOBILE,
                        ),
                ),
                launcherDataResult,
            )
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
