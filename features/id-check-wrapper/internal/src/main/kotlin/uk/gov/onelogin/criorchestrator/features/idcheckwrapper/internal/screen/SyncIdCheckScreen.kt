package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.theme.largePadding
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
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
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onScreenStart(documentVariety)

        viewModel.actions.collect { action ->
            when (action) {
                SyncIdCheckAction.NavigateToRecoverableError -> {
                    navController.navigate(ErrorDestinations.RecoverableError)
                }

                SyncIdCheckAction.NavigateToUnRecoverableError -> {
                    navController.navigate(HandbackDestinations.UnrecoverableError)
                }
            }
        }
    }

    val launchIdCheck: (LauncherData) -> Unit = {
        // DCMAW-11490: Actually call ID Check SDK
    }

    state.let { state ->
        when (state) {
            is SyncIdCheckState.DisplayManualLauncher -> {
                SyncIdCheckScreenContent(
                    displayManualLauncher = true,
                    launcherData =
                        LauncherData(
                            documentType = state.launcherData.documentType,
                            journeyType = state.launcherData.journeyType,
                            sessionId = state.launcherData.sessionId,
                            biometricToken =
                                BiometricToken(
                                    accessToken = state.launcherData.biometricToken.accessToken,
                                    opaqueId = state.launcherData.biometricToken.opaqueId,
                                ),
                        ),
                    onLaunchRequest = { launchIdCheck(state.launcherData) },
                    modifier = modifier,
                )
            }

            SyncIdCheckState.Display -> {
                // This screen does not have any content
            }

            SyncIdCheckState.Loading -> {
                SyncIdCheckScreenContent(
                    displayManualLauncher = false,
                    launcherData = null,
                    onLaunchRequest = {},
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun SyncIdCheckScreenContent(
    displayManualLauncher: Boolean,
    launcherData: LauncherData?,
    onLaunchRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (displayManualLauncher) {
        SyncIdCheckScreenManualLauncherContent(
            launcherData = launcherData,
            onLaunchRequest = onLaunchRequest,
            modifier = modifier,
        )
    } else {
        SyncIdCheckScreenLoadingContent(modifier = modifier)
    }
}

@Suppress("LongParameterList")
@Composable
private fun SyncIdCheckScreenManualLauncherContent(
    launcherData: LauncherData?,
    onLaunchRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "DCMAW-8798 | Android | Confirm Document | Take a photo screen",
        )
        Spacer(modifier = Modifier.padding(largePadding))
        Text(
            text = "Document Type: ${launcherData?.documentType}",
        )
        Text(
            text = "Journey Type: ${launcherData?.journeyType}",
        )
        Text(
            text = "Session ID: ${launcherData?.sessionId}",
        )
        Text(
            text = "Biometric Token Access Token: ${launcherData?.biometricToken?.accessToken}",
        )
        Text(
            text = "Biometric Token Opaque ID: ${launcherData?.biometricToken?.opaqueId}",
        )
        GdsButton(
            text = "Launch ID Check SDK",
            onClick = onLaunchRequest,
            buttonType = ButtonType.Primary,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SyncIdCheckScreenLoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(R.string.loading),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = largePadding),
        )
    }
}

internal data class PreviewParams(
    val displayManualLauncher: Boolean,
)

private class SyncIdCheckScreenPreviewParameterProvider : PreviewParameterProvider<PreviewParams> {
    override val values =
        sequenceOf(
            PreviewParams(
                displayManualLauncher = true,
            ),
            PreviewParams(
                displayManualLauncher = false,
            ),
        )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewSyncIdCheckManualLauncherContent(
    @PreviewParameter(SyncIdCheckScreenPreviewParameterProvider::class)
    params: PreviewParams,
) {
    GdsTheme {
        SyncIdCheckScreenContent(
            displayManualLauncher = params.displayManualLauncher,
            launcherData =
                LauncherData(
                    documentType = DocumentType.NFC_PASSPORT,
                    journeyType = JourneyType.MOBILE_APP_MOBILE,
                    sessionId = "test session ID",
                    biometricToken =
                        BiometricToken(
                            accessToken = "test access token",
                            opaqueId = "test opaque ID",
                        ),
                ),
            onLaunchRequest = {},
        )
    }
}
