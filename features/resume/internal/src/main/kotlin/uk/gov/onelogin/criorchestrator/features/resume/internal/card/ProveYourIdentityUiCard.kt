package uk.gov.onelogin.criorchestrator.features.resume.internal.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.ui.componentsv2.GdsCard
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.dropUnlessResumedUnlessDisabledForTesting

@Composable
internal fun ProveYourIdentityUiCard(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GdsCard(
        title = stringResource(R.string.start_id_check_title),
        body = stringResource(R.string.start_id_check_content),
        buttonText = stringResource(R.string.start_id_check_primary_button),
        modifier = modifier,
        onClick = dropUnlessResumedUnlessDisabledForTesting { onStartClick() },
    )
}

@Composable
@PreviewLightDark
internal fun ProveYourIdentityUiCardPreview() {
    GdsTheme {
        ProveYourIdentityUiCard(
            onStartClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
