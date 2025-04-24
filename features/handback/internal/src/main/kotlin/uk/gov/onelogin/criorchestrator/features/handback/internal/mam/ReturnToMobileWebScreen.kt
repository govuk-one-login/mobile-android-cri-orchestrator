package uk.gov.onelogin.criorchestrator.features.handback.internal.mam

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun ReturnToMobileWebScreen(modifier: Modifier = Modifier) {
    CentreAlignedScreen(
        modifier = modifier,
        title = stringResource(id = R.string.handback_mam_title),
        body =
            persistentListOf(
                CentreAlignedScreenBodyContent.Text(stringResource(id = R.string.handback_mam_body1)),
                CentreAlignedScreenBodyContent.Text(stringResource(id = R.string.handback_mam_body2)),
            ),
        primaryButton =
            CentreAlignedScreenButton(
                text = stringResource(id = R.string.handback_mam_button),
                onClick = {},
                showIcon = true,
            ),
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewReturnToMobileWebScreen() {
    GdsTheme {
        ReturnToMobileWebScreen()
    }
}
