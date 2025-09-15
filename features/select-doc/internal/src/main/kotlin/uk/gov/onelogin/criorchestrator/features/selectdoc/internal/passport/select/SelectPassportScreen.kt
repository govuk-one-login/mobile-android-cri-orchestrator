@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.button.ButtonTypeV2
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.components.FullWidthImage
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SelectPassportScreen(
    viewModel: SelectPassportViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect { event ->
            when (event) {
                SelectPassportAction.NavigateToTypesOfPhotoID ->
                    navController.navigate(
                        SelectDocDestinations.TypesOfPhotoID,
                    )

                SelectPassportAction.NavigateToConfirmation ->
                    navController.navigate(
                        SelectDocDestinations.ConfirmPassport,
                    )

                SelectPassportAction.NavigateToBrp ->
                    navController.navigate(
                        SelectDocDestinations.Brp,
                    )
            }
        }
    }

    SelectPassportScreenContent(
        modifier = modifier,
        onReadMoreClick = viewModel::onReadMoreClick,
        onConfirmSelection = viewModel::onConfirmSelection,
    )
}

@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongMethod", "LongParameterList")
@Composable
internal fun SelectPassportScreenContent(
    onReadMoreClick: () -> Unit,
    onConfirmSelection: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by rememberSaveable { mutableStateOf<Int?>(null) }
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(SelectPassportConstants.titleId),
                    textAlign = GdsHeadingAlignment.LeftAligned,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.selectdocument_passport_body),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    FullWidthImage(
                        painter = painterResource(R.drawable.nfc_passport),
                        contentDescription = stringResource(R.string.selectdocument_passport_imagedescription),
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.selectdocument_passport_expiry),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    GdsButton(
                        text = stringResource(SelectPassportConstants.readMoreButtonTextId),
                        buttonType = ButtonTypeV2.Secondary(),
                        onClick = dropUnlessResumed { onReadMoreClick() },
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
                        items =
                            SelectPassportConstants.options
                                .map { stringResource(it) }
                                .toPersistentList(),
                        selectedItem = selectedItem,
                        onItemSelected = {
                            selectedItem = it
                        },
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = stringResource(SelectPassportConstants.buttonTextId),
                    buttonType = ButtonTypeV2.Primary(),
                    onClick =
                        dropUnlessResumed {
                            selectedItem?.let {
                                onConfirmSelection(it)
                            }
                        },
                    enabled = selectedItem != null,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            forceScroll = true,
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
// DCMAW-8054 | AC5: Welsh translation
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        SelectPassportScreenContent(
            onReadMoreClick = { },
            onConfirmSelection = { },
        )
    }
}
