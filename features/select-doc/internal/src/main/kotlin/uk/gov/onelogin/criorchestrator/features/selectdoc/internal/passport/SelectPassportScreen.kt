package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Image
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.SecondaryButton
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Selection
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenBody.Text
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreenButton
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

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
                SecondaryButton(
                    text = stringResource(R.string.selectdocument_passport_readmore_button),
                    onClick = { },
                ),
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

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        SelectPassportScreen()
    }
}
