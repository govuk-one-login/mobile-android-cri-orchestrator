package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SelectPassportScreen(
    viewModel: SelectPassportViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect { event ->
            when (event) {
                SelectPassportAction.NavigateToTypesOfPhotoID ->
                    navController.navigate(
                        SelectDocumentDestinations.TypesOfPhotoID,
                    )

                SelectPassportAction.NavigateToConfirmation ->
                    navController.navigate(
                        SelectDocumentDestinations.Confirm,
                    )

                SelectPassportAction.NavigateToBRP ->
                    navController.navigate(
                        SelectDocumentDestinations.BRP,
                    )
            }
        }
    }

    SelectPassportScreenContent(
        title = stringResource(state.titleId),
        modifier = modifier,
        readMoreButtonTitle = stringResource(state.readMoreButtonTextId),
        onReadMoreClick = viewModel::onReadMoreClick,
        items =
            state.options
                .map { stringResource(it) }
                .toPersistentList(),
        confirmButtonText = stringResource(state.buttonTextId),
        onConfirmSelection = viewModel::onConfirmSelection,
    )
}

@Composable
@Suppress("LongParameterList")
internal fun SelectPassportScreenContent(
    title: String,
    readMoreButtonTitle: String,
    onReadMoreClick: () -> Unit,
    items: PersistentList<String>,
    confirmButtonText: String,
    onConfirmSelection: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableStateOf<Int?>(null) }
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
                    text = readMoreButtonTitle,
                    onClick = onReadMoreClick,
                ),
                Selection(
                    title =
                        RadioSelectionTitle(
                            stringResource(R.string.selectdocument_passport_title),
                            TitleType.Heading,
                        ),
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = {
                        selectedItem = it
                    },
                ),
            ),
        primaryButton =
            LeftAlignedScreenButton(
                text = confirmButtonText,
                onClick = {
                    selectedItem?.let {
                        onConfirmSelection(it)
                    }
                },
                enabled = selectedItem != null,
            ),
    )
}

@LightDarkBothLocalesPreview
@Composable
// DCMAW-8054 | AC5: Welsh translation
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        SelectPassportScreenContent(
            title = stringResource(R.string.selectdocument_passport_title),
            readMoreButtonTitle = stringResource(R.string.selectdocument_passport_readmore_button),
            onReadMoreClick = { },
            items =
                listOf(
                    R.string.selectdocument_passport_selection_yes,
                    R.string.selectdocument_passport_selection_no,
                ).map { stringResource(it) }.toPersistentList(),
            confirmButtonText = stringResource(R.string.selectdocument_passport_continuebutton),
            onConfirmSelection = { },
        )
    }
}
