package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SyncIdCheckScreen(
    idCheckSdkParameters: IdCheckSdkParameters,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "DCMAW-8798 | Android | Confirm Document | Take a photo screen",
        )
        Text(
            text = "Document Type: ${idCheckSdkParameters.document}",
        )
        Text(
            text = "Journey Type: ${idCheckSdkParameters.journey}",
        )
        Text(
            text = "Session ID: ${idCheckSdkParameters.sessionId}",
        )
        Text(
            text = "Biometric Token Access Token: ${idCheckSdkParameters.bioToken?.accessToken}",
        )
        Text(
            text = "Biometric Token Opaque ID: ${idCheckSdkParameters.bioToken?.opaqueId}",
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewSyncIdCheckScreen() {
    GdsTheme {
        SyncIdCheckScreen(
            IdCheckSdkParameters(
                document = DocumentType.NFC_PASSPORT,
                journey = JourneyType.MOBILE_APP_MOBILE,
                sessionId = "Test Session ID",
                bioToken = BiometricToken(
                    accessToken = "Test Access Token",
                    opaqueId = "Test Opaque ID",
                ),
            )
        )
    }
}
