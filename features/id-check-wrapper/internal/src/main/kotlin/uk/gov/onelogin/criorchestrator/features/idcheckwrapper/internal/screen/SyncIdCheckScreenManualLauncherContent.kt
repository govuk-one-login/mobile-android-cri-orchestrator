package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.backend.BackendMode
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.LauncherData
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongParameterList")
internal fun SyncIdCheckScreenManualLauncherContent(
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
                onClick = dropUnlessResumed { onLaunchRequest() },
                buttonType = ButtonType.Primary,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    )
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
                    backendMode = BackendMode.V2,
                ),
            exitStateOptions = ExitStateOption.entries.map { it.displayName }.toPersistentList(),
            selectedExitState = 0,
            onExitStateSelected = {},
            onLaunchRequest = {},
        )
    }
}
