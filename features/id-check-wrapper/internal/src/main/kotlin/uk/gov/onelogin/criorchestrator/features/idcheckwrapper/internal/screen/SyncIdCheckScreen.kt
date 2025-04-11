package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.theme.largePadding
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.UnavailableIdCheckSdkActivityResultContract
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.toIdCheckSdkActivityParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

/**
 * This screen handles launching of the ID Check SDK journey of the desired journey/document type,
 * and handling of the SDK exit states once the ID Check SDK journey has been completed.
 *
 * This screen will (DCMAW-11498) display a loading spinner while launching the ID Check SDK.
 */
@Suppress("LongMethod")
@Composable
internal fun SyncIdCheckScreen(
    documentVariety: DocumentVariety,
    viewModel: SyncIdCheckViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onScreenStart(documentVariety)
    }
    val activityResultContract by remember {
        derivedStateOf {
            state.let { state ->
                when (state) {
                    is SyncIdCheckState.Display -> state.activityResultContractParameters.toActivityResultContract()
                    SyncIdCheckState.Loading -> UnavailableIdCheckSdkActivityResultContract()
                }
            }
        }
    }
    val launcher =
        rememberLauncherForActivityResult(
            contract = activityResultContract,
            onResult = { viewModel.onIdCheckSdkResult(it) },
        )
    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is SyncIdCheckAction.LaunchIdCheckSdk -> {
                    launcher.launch(action.launcherData.toIdCheckSdkActivityParameters())
                }

                SyncIdCheckAction.NavigateToReturnToMobileWeb ->
                    navController.navigate(
                        HandbackDestinations.ReturnToMobileWeb,
                    )

                SyncIdCheckAction.NavigateToReturnToDesktopWeb ->
                    navController.navigate(
                        HandbackDestinations.ReturnToDesktopWeb,
                    )
            }
        }
    }
    state.let { state ->
        when (state) {
            is SyncIdCheckState.Display -> {
                if (state.manualLauncher != null) {
                    SyncIdCheckScreenManualLauncherContent(
                        documentType = state.launcherData.documentType,
                        journeyType = state.launcherData.journeyType,
                        sessionId = state.launcherData.sessionId,
                        accessToken = state.launcherData.biometricToken.accessToken,
                        opaqueId = state.launcherData.biometricToken.opaqueId,
                        onLaunchRequest = { viewModel.onIdCheckSdkLaunchRequest(state.launcherData) },
                        selectedExitState = state.manualLauncher.selectedExitState,
                        exitStateOptions = state.manualLauncher.exitStateOptions,
                        onExitStateSelected = viewModel::onStubExitStateSelected,
                        modifier = modifier,
                    )
                } else {
                    // This screen does not have any content
                }
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
    exitStateOptions: ImmutableList<String>,
    selectedExitState: Int,
    onExitStateSelected: (Int) -> Unit,
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
        GdsSelection(
            title =
                RadioSelectionTitle(
                    text = "Select ID Check result",
                    titleType = TitleType.BoldText,
                ),
            items = exitStateOptions,
            selectedItem = selectedExitState,
            onItemSelected = onExitStateSelected,
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
            exitStateOptions = persistentListOf("None", "Happy Path"),
            selectedExitState = 0,
            onExitStateSelected = {},
            onLaunchRequest = {},
        )
    }
}
