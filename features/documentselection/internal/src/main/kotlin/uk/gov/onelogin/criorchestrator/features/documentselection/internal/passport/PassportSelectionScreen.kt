package uk.gov.onelogin.criorchestrator.features.documentselection.internal.passport

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.gov.android.ui.theme.m3.GdsTheme

@Composable
fun PassportSelectionScreen(
    @Suppress("UnusedParameter") modifier: Modifier = Modifier
) {
    // todo: uncomment this once the LeftAlignedScreen pull request is merged
    // https://govukverify.atlassian.net/browse/DCMAW-11674
    // https://github.com/govuk-one-login/mobile-android-ui/pull/214

    /*
    LeftAlignedScreen(
        title = stringResource(R.string.documentselection_passport_title),
        modifier = modifier,
        body =
            persistentListOf(
                Text(
                    stringResource(R.string.documentselection_passport_body),
                ),
                Image(
                    image = R.drawable.nfc_passport,
                    contentDescription = stringResource(R.string.documentselection_passport_imagedescription),
                    Modifier.fillMaxWidth(),
                ),
                Text(stringResource(R.string.documentselection_passport_expiry)),
                Text(stringResource(R.string.documentselection_passport_readmore_button)),
                Text(stringResource(R.string.documentselection_passport_selection_title)),
                Text(stringResource(R.string.documentselection_passport_selection_yes)),
                Text(stringResource(R.string.documentselection_passport_selection_no)),
            ),
        primaryButton =
            LeftAlignedScreenButton(
                text = stringResource(R.string.documentselection_passport_continuebutton),
                onClick = {
                },
            ),
    )
     */
}

@PreviewLightDark
@Composable
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        PassportSelectionScreen()
    }
}
