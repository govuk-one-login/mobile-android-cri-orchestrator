@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
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

                SelectPassportAction.NavigateToBrp ->
                    navController.navigate(
                        SelectDocumentDestinations.Brp,
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

@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongMethod", "LongParameterList")
@Composable
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
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = title,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.selectdocument_passport_body),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Image(
                        painter = painterResource(R.drawable.nfc_passport),
                        contentDescription = stringResource(R.string.selectdocument_passport_imagedescription),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.selectdocument_passport_expiry),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    GdsButton(
                        text = readMoreButtonTitle,
                        buttonType = ButtonType.Secondary,
                        onClick = onReadMoreClick,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                        contentModifier = Modifier,
                        textAlign = TextAlign.Left,
                        contentPosition = Arrangement.Start,
                    )
                }
                item {
                    GdsSelection(
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
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = confirmButtonText,
                    buttonType = ButtonType.Primary,
                    onClick = {
                        selectedItem?.let {
                            onConfirmSelection(it)
                        }
                    },
                    enabled = selectedItem != null,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
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
