package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.heading.GdsHeadingStyle
import uk.gov.android.ui.componentsv2.list.GdsBulletedList
import uk.gov.android.ui.componentsv2.list.ListItem
import uk.gov.android.ui.componentsv2.list.ListTitle
import uk.gov.android.ui.componentsv2.list.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.android.ui.theme.spacingSingle
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.components.FullWidthImage
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.util.date.formatGdsDate

@Composable
internal fun TypesOfPhotoIDScreen(
    viewModel: TypesOfPhotoIDViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    TypesOfPhotoIDScreenContent(
        expiryDateText = state.expiryDate.formatGdsDate(),
        modifier = modifier,
    )
}

@Composable
@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
internal fun TypesOfPhotoIDScreenContent(
    expiryDateText: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(TypesOfPhotoIDConstants.titleId),
                    textAlign = GdsHeadingAlignment.LeftAligned,
                    modifier =
                        Modifier.padding(
                            horizontal = horizontalPadding,
                        ),
                )
            },
            arrangement = Arrangement.spacedBy(spacingDouble),
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_body),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                ukPassportItems(horizontalPadding = horizontalPadding)

                nonUkPassportItems(horizontalPadding = horizontalPadding)

                ukBiometricPermitOrCardItems(horizontalPadding = horizontalPadding)

                ukPhotoCardDrivingLicenceItems(horizontalPadding = horizontalPadding, expiryDateText = expiryDateText)
            },
        )
    }
}

private fun LazyListScope.ukPassportItems(horizontalPadding: Dp) {
    item {
        Heading(
            stringResource(R.string.typesofphotoid_ukpassport_title),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        Text(
            text = stringResource(R.string.typesofphotoid_ukpassport_body),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        FullWidthImage(
            painter = painterResource(R.drawable.uk_passport_small),
            contentDescription =
                stringResource(R.string.typesofphotoid_ukpassport_imagedescription),
        )
    }
}

private fun LazyListScope.nonUkPassportItems(horizontalPadding: Dp) {
    item {
        Heading(
            stringResource(R.string.typesofphotoid_nonukpassport_title),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        GdsBulletedList(
            title = listTitle(stringResource(R.string.typesofphotoid_nonukpassport_bulletbody)),
            bulletListItems =
                listOf(
                    R.string.typesofphotoid_nonukpassport_bullet1,
                    R.string.typesofphotoid_nonukpassport_bullet2,
                ).map { ListItem(stringResource(it)) }.toPersistentList(),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        Text(
            text = stringResource(R.string.typesofphotoid_nonukpassport_body),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        FullWidthImage(
            painter = painterResource(R.drawable.nfc_passport_small),
            contentDescription =
                stringResource(R.string.typesofphotoid_nonukpassport_imagedescription),
        )
    }
}

private fun LazyListScope.ukBiometricPermitOrCardItems(horizontalPadding: Dp) {
    item {
        Heading(
            stringResource(R.string.typesofphotoid_brp_title),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        GdsBulletedList(
            title = listTitle(stringResource(R.string.typesofphotoid_brp_bulletbody)),
            bulletListItems =
                listOf(
                    R.string.typesofphotoid_brp_bullet1,
                    R.string.typesofphotoid_brp_bullet2,
                    R.string.typesofphotoid_brp_bullet3,
                ).map { ListItem(stringResource(it)) }.toPersistentList(),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        Text(
            text = stringResource(R.string.typesofphotoid_brp_body),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        FullWidthImage(
            painter = painterResource(R.drawable.brp_small),
            contentDescription = stringResource(R.string.typesofphotoid_brp_imagedescription),
        )
    }
}

private fun LazyListScope.ukPhotoCardDrivingLicenceItems(
    horizontalPadding: Dp,
    expiryDateText: String,
) {
    item {
        Heading(
            stringResource(R.string.typesofphotoid_drivinglicence_title),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        Text(
            text = stringResource(R.string.typesofphotoid_drivinglicence_body_1),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        Text(
            text = stringResource(R.string.typesofphotoid_drivinglicence_body_2),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        GdsBulletedList(
            title = listTitle(stringResource(R.string.typesofphotoid_drivinglicence_bulletbody)),
            bulletListItems =
                persistentListOf(
                    ListItem(
                        stringResource(
                            R.string.typesofphotoid_drivinglicence_bullet1,
                            expiryDateText,
                        ),
                    ),
                    ListItem(
                        stringResource(R.string.typesofphotoid_drivinglicence_bullet2),
                    ),
                ),
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }

    item {
        FullWidthImage(
            painter = painterResource(R.drawable.driving_licence_small),
            contentDescription =
                stringResource(R.string.typesofphotoid_drivinglicence_imagedescription),
        )
    }
}

@Composable
private fun Heading(
    text: String,
    modifier: Modifier = Modifier,
) = GdsHeading(
    text = text,
    textAlign = GdsHeadingAlignment.LeftAligned,
    style = GdsHeadingStyle.Body,
    modifier = modifier.padding(top = spacingSingle),
)

private fun listTitle(title: String) =
    ListTitle(
        title,
        TitleType.Text,
    )

private const val PREVIEW_HEIGHT = 1700

@Preview(heightDp = PREVIEW_HEIGHT, uiMode = UI_MODE_NIGHT_YES)
@Preview(heightDp = PREVIEW_HEIGHT)
@Preview(heightDp = PREVIEW_HEIGHT, locale = "cy")
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        TypesOfPhotoIDScreenContent(
            expiryDateText = "26 December 2025",
        )
    }
}
