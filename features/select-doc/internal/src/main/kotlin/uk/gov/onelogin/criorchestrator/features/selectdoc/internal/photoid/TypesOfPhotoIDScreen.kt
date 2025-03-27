package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import uk.gov.android.ui.componentsv2.bulletedlist.BulletedListTitle
import uk.gov.android.ui.componentsv2.bulletedlist.GdsBulletedList
import uk.gov.android.ui.componentsv2.bulletedlist.TitleType.Text
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.android.ui.theme.spacingSingle
import uk.gov.android.ui.theme.spacingTriple
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

@Composable
@OptIn(UnstableDesignSystemAPI::class)
internal fun TypesOfPhotoIDScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(R.string.typesofphotoid_title),
                    modifier =
                        Modifier.padding(
                            horizontal = horizontalPadding,
                        ),
                )
            },
            arrangement = Arrangement.spacedBy(spacingTriple),
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_body),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    PhotoIDInformation(
                        title = R.string.typesofphotoid_ukpassport_title,
                        body = R.string.typesofphotoid_ukpassport_body,
                        image = R.drawable.uk_passport,
                        imageDescription = R.string.typesofphotoid_ukpassport_imagedescription,
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        title = R.string.typesofphotoid_nonukpassport_title,
                        bulletContent =
                            PhotoIDBulletContent(
                                R.string.typesofphotoid_nonukpassport_bulletbody,
                                persistentListOf(
                                    R.string.typesofphotoid_nonukpassport_bullet1,
                                    R.string.typesofphotoid_nonukpassport_bullet2,
                                ),
                            ),
                        body = R.string.typesofphotoid_nonukpassport_body,
                        image = R.drawable.nfc_passport_wide,
                        imageDescription = R.string.typesofphotoid_nonukpassport_imagedescription,
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        title = R.string.typesofphotoid_brp_title,
                        bulletContent =
                            PhotoIDBulletContent(
                                R.string.typesofphotoid_brp_bulletbody,
                                persistentListOf(
                                    R.string.typesofphotoid_brp_bullet1,
                                    R.string.typesofphotoid_brp_bullet2,
                                    R.string.typesofphotoid_brp_bullet3,
                                ),
                            ),
                        body = R.string.typesofphotoid_brp_body,
                        image = R.drawable.brp,
                        imageDescription = R.string.typesofphotoid_brp_imagedescription,
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        title = R.string.typesofphotoid_drivinglicence_title,
                        bulletContent =
                            PhotoIDBulletContent(
                                R.string.typesofphotoid_drivinglicence_bulletbody,
                                persistentListOf(
                                    R.string.typesofphotoid_drivinglicence_bullet1,
                                    R.string.typesofphotoid_drivinglicence_bullet2,
                                ),
                            ),
                        body = R.string.typesofphotoid_drivinglicence_body,
                        image = R.drawable.brp,
                        imageDescription = R.string.typesofphotoid_drivinglicence_imagedescription,
                        horizontalPadding = horizontalPadding,
                    )
                }
            },
        )
    }
}

internal data class PhotoIDBulletContent(
    @StringRes val body: Int,
    @StringRes val items: ImmutableList<Int>,
)

@Composable
internal fun PhotoIDInformation(
    @StringRes title: Int,
    @StringRes body: Int,
    @DrawableRes image: Int,
    @StringRes imageDescription: Int,
    horizontalPadding: Dp,
    modifier: Modifier = Modifier,
    bulletContent: PhotoIDBulletContent? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacingSingle),
    ) {
        Text(
            text = stringResource(title),
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(spacingDouble),
        ) {
            bulletContent?.let { content ->
                GdsBulletedList(
                    title =
                        BulletedListTitle(
                            stringResource(content.body),
                            Text,
                        ),
                    bulletListItems = content.items.map { stringResource(it) }.toImmutableList(),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            }

            Text(
                text = stringResource(body),
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )

            Image(
                painter = painterResource(image),
                contentDescription = stringResource(imageDescription),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@Preview(heightDp = 1600)
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        TypesOfPhotoIDScreen()
    }
}
