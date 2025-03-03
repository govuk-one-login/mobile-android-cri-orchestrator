package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.ui.pages.dialog.FullScreenDialog
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.dev.publicapi.DevMenuScreen
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.testwrapper.preview.rememberPreviewCriOrchestratorComponent

@Composable
@Suppress("LongMethod")
internal fun DevMenuDialog(
    criOrchestratorComponent: CriOrchestratorComponent,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FullScreenDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        DevMenuScreen(
            component = criOrchestratorComponent,
        )
    }
}

@Composable
@PreviewLightDark
internal fun DevMenuPreview() {
    GdsTheme {
        DevMenuDialog(
            criOrchestratorComponent = rememberPreviewCriOrchestratorComponent(),
            onDismissRequest = {},
        )
    }
}
