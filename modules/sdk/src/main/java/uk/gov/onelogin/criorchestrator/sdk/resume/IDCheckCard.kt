package uk.gov.onelogin.criorchestrator.sdk.resume

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.ui.componentsv2.ContentTileParameters
import uk.gov.android.ui.componentsv2.GdsContentTile
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.sdk.CriOrchestrator
import uk.gov.onelogin.criorchestrator.sdk.R

@Composable
fun IDCheckCard(
    component: CriOrchestrator,
    modifier: Modifier = Modifier,
) {
    GdsContentTile(
        parameters = ContentTileParameters(
            title = R.string.start_id_check_title,
            body = R.string.start_id_check_content,
            text = R.string.start_id_check_primary_button,
        ),
    ) {
        // Identity Proofing and Verification (IPV)
        // TODO: display the ID Check Dialog:
        // DCMAW-10056: Android | Resume IPV | Create 'Start ID Check' modal UI
    }
}

@Composable
@PreviewLightDark
internal fun ContentTilePreview() {
    GdsTheme {
        IDCheckCard(
            component = CriOrchestrator(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
