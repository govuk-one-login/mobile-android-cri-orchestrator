package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.largePadding
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SyncIdCheckScreen(
    documentType: DocumentType,
    viewModel: SyncSdkViewModel,
    modifier: Modifier = Modifier,
) {
    SyncIdCheckContent(
        documentType = documentType,
        journeyType = viewModel.journeyType,
        sessionId = viewModel.session.sessionId,
        accessToken = viewModel.biometricToken.accessToken,
        opaqueId = viewModel.biometricToken.opaqueId,
        modifier = modifier,
    )
}

@Suppress("LongParameterList")
@Composable
private fun SyncIdCheckContent(
    documentType: DocumentType,
    journeyType: JourneyType,
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
        SyncIdCheckContent(
            documentType = DocumentType.NFC_PASSPORT,
            journeyType = JourneyType.MOBILE_APP_MOBILE,
            sessionId = "test session ID",
            accessToken = "test access token",
            opaqueId = "test opaque ID",
        )
    }
}
