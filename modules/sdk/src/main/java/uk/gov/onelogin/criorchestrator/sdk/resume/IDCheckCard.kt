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
    modifier: Modifier,
) {
    GdsContentTile(
        parameters = ContentTileParameters(
            title = R.string.start_id_check_title,
            body = R.string.start_id_check_content,
            text = R.string.start_id_check_primary_button,
        ),
    ) {
        // on click
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
