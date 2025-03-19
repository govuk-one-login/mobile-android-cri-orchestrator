package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
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
internal fun SelectPassportScreen(
    viewModel: SelectPassportViewModel,
    modifier: Modifier = Modifier
) {
    SelectPassportScreenContent(
        title = stringResource(viewModel.titleId),
        modifier = modifier,
        items = viewModel.options
            .map { stringResource(it) }.toPersistentList(),
        selectedItem = viewModel.selectedItem,
        onItemSelected = viewModel::onItemSelected,
        confirmButtonText = stringResource(viewModel.buttonTextId),
        onConfirmSelection = viewModel::onConfirmSelection
    )
}

@Composable
internal fun SelectPassportScreenContent(
    title: String,
    items: PersistentList<String>,
    selectedItem: Int?,
    onItemSelected: (Int) -> Unit,
    confirmButtonText: String,
    onConfirmSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    LeftAlignedScreen(
        title = title,
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
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = onItemSelected,
                ),
            ),
        primaryButton =
            LeftAlignedScreenButton(
                text = confirmButtonText,
                onClick = onConfirmSelection,
            ),
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        SelectPassportScreenContent(
            title = stringResource(R.string.selectdocument_passport_title),
            items = listOf(
                R.string.selectdocument_passport_selection_yes,
                R.string.selectdocument_passport_selection_no,
            ).map { stringResource(it) }.toPersistentList(),
            selectedItem = null,
            onItemSelected = { },
            confirmButtonText = stringResource(R.string.selectdocument_passport_continuebutton),
            onConfirmSelection = { },
        )
    }
}
