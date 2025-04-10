package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.data.LauncherDataReader
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class SyncIdCheckViewModelTest {
    private val session = Session.createTestInstance()
    private val documentVariety = DocumentVariety.NFC_PASSPORT
    private val viewModel =
        SyncIdCheckViewModel(
            launcherDataReader =
                LauncherDataReader(
                    sessionStore =
                        FakeSessionStore(
                            session = session,
                        ),
                ),
        )

    @Test
    fun `before screen is started, starts loading`() =
        runTest {
            viewModel.state.test {
                assertEquals(SyncIdCheckState.Loading, awaitItem())
                cancel()
            }
        }

    @Test
    fun `when screen is started, it loads the manual launcher`() =
        runTest {
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
                                        accessToken = "TODO",
                                        opaqueId = "TODO",
                                    ),
                            ),
                    ),
                    awaitItem(),
                )
            }
        }
}
