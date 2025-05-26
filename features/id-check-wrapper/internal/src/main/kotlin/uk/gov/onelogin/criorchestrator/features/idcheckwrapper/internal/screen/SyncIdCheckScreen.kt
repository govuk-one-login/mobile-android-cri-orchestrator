package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import uk.gov.android.ui.patterns.loadingscreen.LoadingScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.UnableToConfirmIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.UnavailableIdCheckSdkActivityResultContract
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.toIdCheckSdkActivityParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.libraries.composeutils.OneTimeLaunchedEffect

/**
 * This screen handles launching of the ID Check SDK journey of the desired journey/document type,
 * and handling of the SDK exit states once the ID Check SDK journey has been completed.
 */
@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongMethod")
@Composable
internal fun SyncIdCheckScreen(
    documentVariety: DocumentVariety,
    viewModel: SyncIdCheckViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val activityResultContract by remember {
        derivedStateOf {
            state.let { state ->
                when (state) {
                    is SyncIdCheckState.Display -> state.activityResultContractParameters.toActivityResultContract()
                    SyncIdCheckState.Loading -> UnavailableIdCheckSdkActivityResultContract()
                    SyncIdCheckState.DisplayStubBiometricToken -> UnavailableIdCheckSdkActivityResultContract()
                }
            }
        }
    }
    val launcher =
        rememberLauncherForActivityResult(
            contract = activityResultContract,
            onResult = { viewModel.onIdCheckSdkResult(it) },
        )

    SyncIdCheckActionHandler(
        viewModel.actions,
        launcher,
        navController,
    )

    state.let { state ->
        when (state) {
            is SyncIdCheckState.Display -> {
                if (state.manualLauncher != null) {
                    SyncIdCheckScreenManualLauncherContent(
                        modifier = modifier,
                        launcherData = state.launcherData,
                        selectedExitState = state.manualLauncher.selectedExitState,
                        exitStateOptions = state.manualLauncher.exitStateOptions,
                        onLaunchRequest = { viewModel.onIdCheckSdkLaunchRequest(state.launcherData) },
                        onExitStateSelected = { viewModel.onStubExitStateSelected(it) },
                    )
                } else {
                    SyncIdCheckAutomaticLauncherContent(
                        onLaunchRequest = { viewModel.onIdCheckSdkLaunchRequest(state.launcherData) },
                    )
                }
            }

            SyncIdCheckState.Loading -> LoadingScreen()

            SyncIdCheckState.DisplayStubBiometricToken ->
                SyncIdCheckManualBiometricTokenContent(
                    onItemSelected = {
                        viewModel.onStubGetBiometricToken(
                            documentVariety = documentVariety,
                            selectedItem = it,
                        )
                    },
                    modifier = modifier,
                )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onScreenStart(documentVariety)
    }
}

@Composable
private fun SyncIdCheckActionHandler(
    actions: Flow<SyncIdCheckAction>,
    launcher: ManagedActivityResultLauncher<IdCheckSdkParameters, IdCheckSdkExitState>,
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        actions.collect { action ->
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

                is SyncIdCheckAction.NavigateToAbortedRedirectToMobileWebHolder ->
                    navController.navigate(
                        AbortDestinations.AbortedRedirectToMobileWebHolder(
                            redirectUri = action.redirectUri,
                        ),
                    )

                SyncIdCheckAction.NavigateToAbortedReturnToDesktopWeb ->
                    navController.navigate(
                        AbortDestinations.AbortedReturnToDesktopWeb,
                    )

                SyncIdCheckAction.NavigateToRecoverableError ->
                    navController.navigate(ErrorDestinations.RecoverableError)

                SyncIdCheckAction.NavigateToUnrecoverableError ->
                    navController.navigate(HandbackDestinations.UnrecoverableError)

                SyncIdCheckAction.NavigateToUnableToConfirmIdentityDesktop ->
                    navController.navigate(UnableToConfirmIdentityDestinations.UnableToConfirmIdentityDesktop)

                SyncIdCheckAction.NavigateToUnableToConfirmIdentityMobile ->
                    navController.navigate(UnableToConfirmIdentityDestinations.UnableToConfirmIdentityMobile)
            }
        }
    }
}

@Composable
private fun SyncIdCheckAutomaticLauncherContent(onLaunchRequest: () -> Unit) {
    OneTimeLaunchedEffect {
        onLaunchRequest()
    }
}

@PreviewLightDark
@Composable
internal fun PreviewSyncIdCheckAutomaticLauncherContent() {
    GdsTheme {
        SyncIdCheckAutomaticLauncherContent(
            onLaunchRequest = {},
        )
    }
}
