package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.theme.largePadding
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
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
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onScreenStart(documentVariety)
    }
    val launchIdCheck: (LauncherData) -> Unit = {
        // DCMAW-11490: Actually call ID Check SDK
    }
    state.let { state ->
        when (state) {
            is SyncIdCheckState.DisplayManualLauncher -> {
                SyncIdCheckScreenManualLauncherContent(
                    documentType = state.launcherData.documentType,
                    journeyType = state.launcherData.journeyType,
                    sessionId = state.launcherData.sessionId,
                    accessToken = state.launcherData.biometricToken.accessToken,
                    opaqueId = state.launcherData.biometricToken.opaqueId,
                    onLaunchRequest = { launchIdCheck(state.launcherData) },
                    modifier = modifier,
                )
            }

            SyncIdCheckState.Display -> {
                // This screen does not have any content
            }
            SyncIdCheckState.Loading -> Text(text = "Loading")
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun SyncIdCheckScreenManualLauncherContent(
    documentType: DocumentType,
    journeyType: JourneyType,
    sessionId: String,
    accessToken: String,
    opaqueId: String,
    onLaunchRequest: () -> Unit,
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
        GdsButton(
            text = "Launch ID Check SDK",
            onClick = onLaunchRequest,
            buttonType = ButtonType.Primary,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewSyncIdCheckManualLauncherContent() {
    GdsTheme {
        SyncIdCheckScreenManualLauncherContent(
            documentType = DocumentType.NFC_PASSPORT,
            journeyType = JourneyType.MOBILE_APP_MOBILE,
            sessionId = "test session ID",
            accessToken = "test access token",
            opaqueId = "test opaque ID",
            onLaunchRequest = {},
        )
    }
}
