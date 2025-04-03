package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.largePadding
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.nav.toDocumentType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

/**
 * This screen handles launching of the ID Check SDK journey of the desired journey/document type,
 * and handling of the SDK exit states once the ID Check SDK journey has been completed.
 *
 * This screen will (DCMAW-11498) display a loading spinner while launching the ID Check SDK.
 */
@Composable
internal fun SyncIdCheckScreen(
    documentVariety: DocumentVariety,
    viewModel: SyncIdCheckViewModel,
    modifier: Modifier = Modifier,
) {
    val session by viewModel.session.collectAsState()
    val journeyType by viewModel.journeyType.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    SyncIdCheckScreenContent(
        documentType = documentVariety.toDocumentType(),
        journeyType = journeyType?.toString() ?: "loading journey type",
        sessionId = session?.sessionId ?: "loading session id",
        accessToken = viewModel.biometricToken.accessToken,
        opaqueId = viewModel.biometricToken.opaqueId,
        modifier = modifier,
    )
}

// DCMAW-11490: Actually call ID Check SDK and build bypass
@Suppress("LongParameterList")
@Composable
private fun SyncIdCheckScreenContent(
    documentType: DocumentType,
    journeyType: String,
    sessionId: String,
    accessToken: String,
    opaqueId: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "DCMAW-8798 | Android | Confirm Document | Take a photo screen",
        )
        Spacer(modifier = Modifier.padding(largePadding))
        Text(
            text = "Document Type: $documentType",
        )
        Text(
            text = "Journey Type: $journeyType",
        )
        Text(
            text = "Session ID: $sessionId",
        )
        Text(
            text = "Biometric Token Access Token: $accessToken",
        )
        Text(
            text = "Biometric Token Opaque ID: $opaqueId",
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewSyncIdCheckScreen() {
    GdsTheme {
        SyncIdCheckScreenContent(
            documentType = DocumentType.NFC_PASSPORT,
            journeyType = JourneyType.MOBILE_APP_MOBILE.toString(),
            sessionId = "test session ID",
            accessToken = "test access token",
            opaqueId = "test opaque ID",
        )
    }
}
