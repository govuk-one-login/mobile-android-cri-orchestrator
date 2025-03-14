package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Image
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Text
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenButton
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

@Composable
fun SelectPassportScreen(modifier: Modifier = Modifier) {
    LeftAlignedScreen(
        title = stringResource(R.string.selectdocument_passport_title),
        modifier = modifier,
        body =
            persistentListOf(
                Text(
                    stringResource(R.string.selectdocument_passport_body),
                ),
                Image(
                    image = R.drawable.nfc_passport,
                    contentDescription = stringResource(R.string.selectdocument_passport_imagedescription),
                    Modifier.fillMaxWidth(),
                ),
                Text(stringResource(R.string.selectdocument_passport_expiry)),
                Text(stringResource(R.string.selectdocument_passport_readmore_button)),
                // todo: uncomment this once the Selection component pull request is merged
                // - https://govukverify.atlassian.net/browse/DCMAW-11571
                // - https://github.com/govuk-one-login/mobile-android-ui/pull/215
                //
                // Selection(
                //    title: RadioSelectionTitle(
                //        stringResource(R.string.selectdocument_passport_title),
                //         TitleType.Heading
                //    ),
                //    radioSelectionItems: persistentListOf(
                //        R.string.selectdocument_passport_selection_yes,
                //        R.string.selectdocument_passport_selection_no,
                //    ).map { stringResource(it) },
                // )
            ),
        primaryButton =
            LeftAlignedScreenButton(
                text = stringResource(R.string.selectdocument_passport_continuebutton),
                onClick = {
                },
            ),
    )
}

@PreviewLightDark
@Composable
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        SelectPassportScreen()
    }
}
