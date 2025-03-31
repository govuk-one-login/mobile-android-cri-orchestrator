package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.bulletedlist.BulletedListTitle
import uk.gov.android.ui.componentsv2.bulletedlist.GdsBulletedList
import uk.gov.android.ui.componentsv2.bulletedlist.TitleType.Text
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.warning.GdsWarningText
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun SelectBrpScreen(
    viewModel: SelectBrpViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect { event ->
            when (event) {
                SelectBrpAction.NavigateToTypesOfPhotoID -> {
                    navController.navigate(
                        SelectDocumentDestinations.TypesOfPhotoID,
                    )
                }

                SelectBrpAction.NavigateToBrpConfirmation -> {
                    navController.navigate(
                        SelectDocumentDestinations.BrpConfirmation,
                    )
                }

                SelectBrpAction.NavigateToDrivingLicence -> {
                    navController.navigate(
                        SelectDocumentDestinations.DrivingLicence,
                    )
                }
            }
        }
    }

    SelectBrpScreenContent(
        onReadMoreClicked = viewModel::onReadMoreClicked,
        onContinueClicked = viewModel::onContinueClicked,
        modifier = modifier,
    )
}

@Suppress("LongMethod", "LongParameterList")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun SelectBrpScreenContent(
    onReadMoreClicked: () -> Unit,
    onContinueClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableStateOf<Int?>(null) }

    LeftAlignedScreen(
        modifier = modifier,
        title = { horizontalPadding ->
            GdsHeading(
                stringResource(SelectBrpConstants.titleId),
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        },
        body = { horizontalPadding ->
            item {
                GdsBulletedList(
                    persistentListOf(
                        stringResource(R.string.selectdocument_brp_bullet_brp),
                        stringResource(R.string.selectdocument_brp_bullet_brc),
                        stringResource(R.string.selectdocument_brp_bullet_fwp),
                    ),
                    title =
                        BulletedListTitle(
                            text = stringResource(R.string.selectdocument_brp_bullet_title),
                            titleType = Text,
                        ),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            }

            item {
                GdsWarningText(
                    stringResource(R.string.selectdocument_brp_expiry),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            }

            item {
                GdsButton(
                    text = stringResource(SelectBrpConstants.readMoreButtonTextId),
                    buttonType = ButtonType.Secondary,
                    onClick = onReadMoreClicked,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    textAlign = TextAlign.Start,
                    contentPosition = Arrangement.Start,
                    contentModifier = Modifier,
                )
            }

            item {
                GdsSelection(
                    items =
                        SelectBrpConstants.selectionItems
                            .map { stringResource(it) }
                            .toPersistentList(),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it },
                    title =
                        RadioSelectionTitle(
                            stringResource(R.string.selectdocument_brp_selection_title),
                            uk.gov.android.ui.componentsv2.inputs.radio.TitleType.BoldText,
                        ),
                )
            }
        },
        primaryButton = {
            GdsButton(
                stringResource(SelectBrpConstants.continueButtonTextId),
                buttonType = ButtonType.Primary,
                onClick = {
                    selectedItem?.let {
                        onContinueClicked(it)
                    }
                },
                enabled = selectedItem != null,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewSelectBrpScreen() {
    GdsTheme {
        SelectBrpScreenContent(
            {},
            {},
        )
    }
}
