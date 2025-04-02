package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import uk.gov.android.ui.componentsv2.bulletedlist.BulletedListTitle
import uk.gov.android.ui.componentsv2.bulletedlist.GdsBulletedList
import uk.gov.android.ui.componentsv2.bulletedlist.TitleType.Text
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.android.ui.theme.spacingSingle
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.components.FullWidthImage

@Composable
internal fun TypesOfPhotoIDScreen(
    viewModel: TypesOfPhotoIDViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    TypesOfPhotoIDScreenContent(modifier)
}

@Composable
@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
internal fun TypesOfPhotoIDScreenContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(TypesOfPhotoIDConstants.titleId),
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

                item {
                    PhotoIDInformation(
                        title = stringResource(R.string.typesofphotoid_ukpassport_title),
                        body = stringResource(R.string.typesofphotoid_ukpassport_body),
                        image = {
                            FullWidthImage(
                                painter = painterResource(R.drawable.uk_passport_small),
                                contentDescription =
                                    stringResource(R.string.typesofphotoid_ukpassport_imagedescription),
                            )
                        },
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        title = stringResource(R.string.typesofphotoid_nonukpassport_title),
                        body = stringResource(R.string.typesofphotoid_nonukpassport_body),
                        image = {
                            FullWidthImage(
                                painter = painterResource(R.drawable.nfc_passport_small),
                                contentDescription =
                                    stringResource(R.string.typesofphotoid_nonukpassport_imagedescription),
                            )
                        },
                        bulletedList = {
                            PhotoIDBulletedList(
                                stringResource(R.string.typesofphotoid_nonukpassport_bulletbody),
                                listOf(
                                    R.string.typesofphotoid_nonukpassport_bullet1,
                                    R.string.typesofphotoid_nonukpassport_bullet2,
                                ).map { stringResource(it) }.toPersistentList(),
                                Modifier.padding(horizontal = horizontalPadding),
                            )
                        },
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        title = stringResource(R.string.typesofphotoid_brp_title),
                        body = stringResource(R.string.typesofphotoid_brp_body),
                        image = {
                            FullWidthImage(
                                painter = painterResource(R.drawable.brp_small),
                                contentDescription = stringResource(R.string.typesofphotoid_brp_imagedescription),
                            )
                        },
                        bulletedList = {
                            PhotoIDBulletedList(
                                stringResource(R.string.typesofphotoid_brp_bulletbody),
                                listOf(
                                    R.string.typesofphotoid_brp_bullet1,
                                    R.string.typesofphotoid_brp_bullet2,
                                    R.string.typesofphotoid_brp_bullet3,
                                ).map { stringResource(it) }.toPersistentList(),
                                Modifier.padding(horizontal = horizontalPadding),
                            )
                        },
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        title = stringResource(R.string.typesofphotoid_drivinglicence_title),
                        body = stringResource(R.string.typesofphotoid_drivinglicence_body),
                        image = {
                            FullWidthImage(
                                painter = painterResource(R.drawable.driving_licence_small),
                                contentDescription =
                                    stringResource(R.string.typesofphotoid_drivinglicence_imagedescription),
                            )
                        },
                        bulletedList = {
                            PhotoIDBulletedList(
                                stringResource(R.string.typesofphotoid_drivinglicence_bulletbody),
                                persistentListOf(
                                    stringResource(R.string.typesofphotoid_drivinglicence_bullet1),
                                    stringResource(R.string.typesofphotoid_drivinglicence_bullet2),
                                ),
                                Modifier.padding(horizontal = horizontalPadding),
                            )
                        },
                        horizontalPadding = horizontalPadding,
                    )
                }
            },
        )
    }
}

@Composable
@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongParameterList")
private fun PhotoIDInformation(
    title: String,
    body: String,
    horizontalPadding: Dp,
    modifier: Modifier = Modifier,
    image: @Composable () -> Unit = { },
    bulletedList: @Composable () -> Unit = { },
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacingSingle),
    ) {
        GdsHeading(
            text = title,
            fontWeight = FontWeight.W700,
            style = LocalTextStyle.current,
            modifier =
                Modifier
                    .padding(horizontal = horizontalPadding)
                    .padding(top = spacingSingle),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(spacingDouble),
        ) {
            bulletedList()

            Text(
                text = body,
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )

            image()
        }
    }
}

@Composable
private fun PhotoIDBulletedList(
    title: String,
    items: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    GdsBulletedList(
        title =
            BulletedListTitle(
                title,
                Text,
            ),
        bulletListItems = items,
        modifier = modifier,
    )
}

@Preview(heightDp = 1600, uiMode = UI_MODE_NIGHT_YES)
@Preview(heightDp = 1600)
@Preview(heightDp = 1600, locale = "cy")
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        TypesOfPhotoIDScreenContent()
    }
}
