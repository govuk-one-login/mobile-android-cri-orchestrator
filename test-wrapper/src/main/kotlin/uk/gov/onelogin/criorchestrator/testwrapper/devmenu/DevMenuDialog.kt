package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.Dialog
import uk.gov.android.ui.patterns.dialog.FullScreenDialogue
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.dev.publicapi.DevMenuScreen
import uk.gov.onelogin.criorchestrator.libraries.composeutils.fullScreenDialogProperties
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph
import uk.gov.onelogin.criorchestrator.testwrapper.preview.rememberPreviewCriOrchestratorGraph

@Composable
@Suppress("LongMethod")
internal fun DevMenuDialog(
    criOrchestratorGraph: CriOrchestratorGraph,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) = Dialog(
    onDismissRequest = onDismissRequest,
    properties = fullScreenDialogProperties,
) {
    FullScreenDialogue(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        DevMenuScreen(
            graph = criOrchestratorGraph,
        )
    }
}

@Composable
@PreviewLightDark
internal fun DevMenuPreview() {
    GdsTheme {
        DevMenuDialog(
            criOrchestratorGraph = rememberPreviewCriOrchestratorGraph(),
            onDismissRequest = {},
        )
    }
}
