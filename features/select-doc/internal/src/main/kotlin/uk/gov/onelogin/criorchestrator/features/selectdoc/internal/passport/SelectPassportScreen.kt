package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Image
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Selection
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
                Selection(
                    title =
                        RadioSelectionTitle(
                            stringResource(R.string.selectdocument_passport_title),
                            TitleType.Heading,
                        ),
                    items =
                        persistentListOf(
                            stringResource(R.string.selectdocument_passport_selection_yes),
                            stringResource(R.string.selectdocument_passport_selection_no),
                        ),
                    selectedItem = null,
                    onItemSelected = { },
                ),
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
