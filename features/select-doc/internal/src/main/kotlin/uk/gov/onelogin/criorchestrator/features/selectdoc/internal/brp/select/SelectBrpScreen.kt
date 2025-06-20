package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.list.GdsBulletedList
import uk.gov.android.ui.componentsv2.list.ListItem
import uk.gov.android.ui.componentsv2.list.ListTitle
import uk.gov.android.ui.componentsv2.list.TitleType
import uk.gov.android.ui.componentsv2.warning.GdsWarningText
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
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
                        SelectDocDestinations.TypesOfPhotoID,
                    )
                }

                SelectBrpAction.NavigateToBrpConfirmation -> {
                    navController.navigate(
                        SelectDocDestinations.ConfirmBrp,
                    )
                }

                SelectBrpAction.NavigateToDrivingLicence -> {
                    navController.navigate(
                        SelectDocDestinations.DrivingLicence,
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
    var selectedItem by rememberSaveable { mutableStateOf<Int?>(null) }

    LeftAlignedScreen(
        modifier = modifier,
        title = { horizontalPadding ->
            GdsHeading(
                stringResource(SelectBrpConstants.titleId),
                textAlign = GdsHeadingAlignment.LeftAligned,
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        },
        body = { horizontalPadding ->
            item {
                GdsBulletedList(
                    bulletListItems =
                        persistentListOf(
                            ListItem(
                                stringResource(R.string.selectdocument_brp_bullet_brp),
                            ),
                            ListItem(
                                stringResource(R.string.selectdocument_brp_bullet_brc),
                            ),
                            ListItem(
                                stringResource(R.string.selectdocument_brp_bullet_fwp),
                            ),
                        ),
                    title =
                        ListTitle(
                            text = stringResource(R.string.selectdocument_brp_bullet_title),
                            titleType = TitleType.Text,
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
                    onClick = dropUnlessResumed { onReadMoreClicked() },
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
                onClick =
                    dropUnlessResumed {
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
            onReadMoreClicked = {},
            onContinueClicked = {},
        )
    }
}
