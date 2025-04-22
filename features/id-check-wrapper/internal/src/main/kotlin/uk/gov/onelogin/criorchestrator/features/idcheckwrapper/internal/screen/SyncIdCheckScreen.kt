package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.patterns.loadingscreen.LoadingScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.R
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.UnavailableIdCheckSdkActivityResultContract
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity.toIdCheckSdkActivityParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview
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

            SyncIdCheckState.Loading -> Loading()

            SyncIdCheckState.DisplayStubBiometricToken ->
                BiometricTokenDebug(
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

                SyncIdCheckAction.NavigateToConfirmAbortToDesktopWeb,
                SyncIdCheckAction.NavigateToConfirmAbortToMobileWeb,
                ->
                    navController.navigate(
                        HandbackDestinations.ConfirmAbort,
                    )

                SyncIdCheckAction.NavigateToRecoverableError -> {
                    navController.navigate(ErrorDestinations.RecoverableError)
                }

                SyncIdCheckAction.NavigateToUnrecoverableError -> {
                    navController.navigate(HandbackDestinations.UnrecoverableError)
                }
            }
        }
    }
}

@Composable
@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongParameterList")
private fun SyncIdCheckScreenManualLauncherContent(
    launcherData: LauncherData,
    selectedExitState: Int,
    exitStateOptions: ImmutableList<String>,
    onExitStateSelected: (Int) -> Unit,
    onLaunchRequest: () -> Unit,
    modifier: Modifier = Modifier,
) = Surface(
    color = MaterialTheme.colorScheme.background,
) {
    LeftAlignedScreen(
        modifier = modifier,
        title = { horizontalPadding ->
            GdsHeading(
                text = "Select an ID check result",
                textAlign = GdsHeadingAlignment.LeftAligned,
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        },
        body = { horizontalPadding ->
            item {
                DebugData(
                    documentType = launcherData.documentType,
                    journeyType = launcherData.journeyType,
                    sessionId = launcherData.sessionId,
                    accessToken = launcherData.biometricToken.accessToken,
                    opaqueId = launcherData.biometricToken.opaqueId,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            }
            item {
                GdsSelection(
                    title =
                        RadioSelectionTitle(
                            text = "Select an ID check result",
                            titleType = TitleType.BoldText,
                        ),
                    items = exitStateOptions,
                    selectedItem = selectedExitState,
                    onItemSelected = onExitStateSelected,
                )
            }
        },
        primaryButton = {
            GdsButton(
                text = "Launch ID Check SDK",
                onClick = onLaunchRequest,
                buttonType = ButtonType.Primary,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    )
}

@Composable
private fun BiometricTokenDebug(
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by rememberSaveable { mutableStateOf<Int?>(null) }
    Column(modifier = modifier) {
        GdsSelection(
            title =
                RadioSelectionTitle(
                    "Select get biometric token result",
                    titleType = TitleType.BoldText,
                ),
            items =
                persistentListOf(
                    "Success",
                    "Recoverable error",
                    "Unrecoverable error",
                ),
            selectedItem = selectedItem,
            onItemSelected = {
                onItemSelected(it)
                selectedItem = it
            },
        )
    }
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
private fun Loading(modifier: Modifier = Modifier) {
    LoadingScreen(
        text = stringResource(R.string.loading),
        modifier = modifier,
    )
}

@Composable
private fun SyncIdCheckAutomaticLauncherContent(onLaunchRequest: () -> Unit) {
    OneTimeLaunchedEffect {
        onLaunchRequest()
    }
}

@Composable
@Suppress("LongParameterList")
private fun DebugData(
    documentType: DocumentType,
    journeyType: JourneyType,
    sessionId: String,
    accessToken: String,
    opaqueId: String,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
) {
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

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewSyncIdCheckManualLauncherContent() {
    GdsTheme {
        SyncIdCheckScreenManualLauncherContent(
            launcherData =
                LauncherData(
                    documentType = DocumentType.NFC_PASSPORT,
                    session =
                        Session(
                            sessionId = "test session ID",
                            state = "test state",
                        ),
                    biometricToken =
                        BiometricToken(
                            accessToken = "test access token",
                            opaqueId = "test opaque ID",
                        ),
                ),
            exitStateOptions = ExitStateOption.entries.map { it.displayName }.toPersistentList(),
            selectedExitState = 0,
            onExitStateSelected = {},
            onLaunchRequest = {},
        )
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

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewLoading() {
    GdsTheme {
        Loading()
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewBiometricTokenDebug() {
    GdsTheme {
        BiometricTokenDebug(
            onItemSelected = {},
        )
    }
}
