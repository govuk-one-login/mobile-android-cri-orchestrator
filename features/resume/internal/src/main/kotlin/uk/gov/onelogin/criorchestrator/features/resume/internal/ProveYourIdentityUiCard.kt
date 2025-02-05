package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.ui.componentsv2.GdsCard
import uk.gov.android.ui.theme.m3.GdsTheme

@Composable
internal fun ProveYourIdentityUiCard(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GdsCard(
        title = stringResource(R.string.start_id_check_title),
        body = stringResource(R.string.start_id_check_content),
        buttonText = stringResource(R.string.start_id_check_primary_button),
        modifier = modifier.testTag(PROVE_YOUR_IDENTITY_UI_CARD_TEST_TAG),
        onClick = onStartClick,
    )
}

const val PROVE_YOUR_IDENTITY_UI_CARD_TEST_TAG = "ProveYourIdentityUiCard"

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
