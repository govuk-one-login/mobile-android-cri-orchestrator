package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.runtime.LaunchedEffect
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
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

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
                        content =
                            PhotoIDContent(
                                title = R.string.typesofphotoid_ukpassport_title,
                                body = R.string.typesofphotoid_ukpassport_body,
                                image = R.drawable.uk_passport,
                                imageDescription = R.string.typesofphotoid_ukpassport_imagedescription,
                            ),
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        content =
                            PhotoIDContent(
                                title = R.string.typesofphotoid_nonukpassport_title,
                                body = R.string.typesofphotoid_nonukpassport_body,
                                image = R.drawable.nfc_passport_wide,
                                imageDescription = R.string.typesofphotoid_nonukpassport_imagedescription,
                            ),
                        bulletContent =
                            PhotoIDBulletContent(
                                R.string.typesofphotoid_nonukpassport_bulletbody,
                                persistentListOf(
                                    R.string.typesofphotoid_nonukpassport_bullet1,
                                    R.string.typesofphotoid_nonukpassport_bullet2,
                                ),
                            ),
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        content =
                            PhotoIDContent(
                                title = R.string.typesofphotoid_brp_title,
                                body = R.string.typesofphotoid_brp_body,
                                image = R.drawable.brp,
                                imageDescription = R.string.typesofphotoid_brp_imagedescription,
                            ),
                        bulletContent =
                            PhotoIDBulletContent(
                                R.string.typesofphotoid_brp_bulletbody,
                                persistentListOf(
                                    R.string.typesofphotoid_brp_bullet1,
                                    R.string.typesofphotoid_brp_bullet2,
                                    R.string.typesofphotoid_brp_bullet3,
                                ),
                            ),
                        horizontalPadding = horizontalPadding,
                    )
                }

                item {
                    PhotoIDInformation(
                        content =
                            PhotoIDContent(
                                title = R.string.typesofphotoid_drivinglicence_title,
                                body = R.string.typesofphotoid_drivinglicence_body,
                                image = R.drawable.driving_licence,
                                imageDescription = R.string.typesofphotoid_drivinglicence_imagedescription,
                            ),
                        bulletContent =
                            PhotoIDBulletContent(
                                R.string.typesofphotoid_drivinglicence_bulletbody,
                                persistentListOf(
                                    R.string.typesofphotoid_drivinglicence_bullet1,
                                    R.string.typesofphotoid_drivinglicence_bullet2,
                                ),
                            ),
                        horizontalPadding = horizontalPadding,
                    )
                }
            },
        )
    }
}

internal data class PhotoIDContent(
    @StringRes val title: Int,
    @StringRes val body: Int,
    @DrawableRes val image: Int,
    @StringRes val imageDescription: Int,
)

internal data class PhotoIDBulletContent(
    @StringRes val body: Int,
    @StringRes val items: ImmutableList<Int>,
)

@Composable
internal fun PhotoIDInformation(
    content: PhotoIDContent,
    horizontalPadding: Dp,
    modifier: Modifier = Modifier,
    bulletContent: PhotoIDBulletContent? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacingSingle),
    ) {
        Text(
            text = stringResource(content.title),
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
                text = stringResource(content.body),
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )

            Image(
                painter = painterResource(content.image),
                contentDescription = stringResource(content.imageDescription),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@LightDarkBothLocalesPreview
@Preview(heightDp = 1600, uiMode = UI_MODE_NIGHT_YES)
@Preview(heightDp = 1600)
@Preview(heightDp = 1600, locale = "cy")
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        TypesOfPhotoIDScreenContent()
    }
}
