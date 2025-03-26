package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.compose.foundation.Image
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
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.bulletedlist.BulletedListTitle
import uk.gov.android.ui.componentsv2.bulletedlist.GdsBulletedList
import uk.gov.android.ui.componentsv2.bulletedlist.TitleType.Text
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
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
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_body),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_ukpassport_title),
                        fontWeight = FontWeight.W700,
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
                    Image(
                        painter = painterResource(R.drawable.uk_passport),
                        contentDescription = stringResource(R.string.typesofphotoid_ukpassport_imagedescription),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_nonukpassport_title),
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    GdsBulletedList(
                        title =
                            BulletedListTitle(
                                stringResource(R.string.typesofphotoid_nonukpassport_bulletbody),
                                Text,
                            ),
                        bulletListItems =
                            persistentListOf(
                                stringResource(R.string.typesofphotoid_nonukpassport_bullet1),
                                stringResource(R.string.typesofphotoid_nonukpassport_bullet2),
                            ),
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
                    Image(
                        painter = painterResource(R.drawable.nfc_passport_wide),
                        contentDescription = stringResource(R.string.typesofphotoid_nonukpassport_imagedescription),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_brp_title),
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    GdsBulletedList(
                        title =
                            BulletedListTitle(
                                stringResource(R.string.typesofphotoid_brp_bulletbody),
                                Text,
                            ),
                        bulletListItems =
                            persistentListOf(
                                stringResource(R.string.typesofphotoid_brp_bullet1),
                                stringResource(R.string.typesofphotoid_brp_bullet2),
                                stringResource(R.string.typesofphotoid_brp_bullet3),
                            ),
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
                    Image(
                        painter = painterResource(R.drawable.brp),
                        contentDescription = stringResource(R.string.typesofphotoid_brp_imagedescription),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_drivinglicence_title),
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    GdsBulletedList(
                        title =
                            BulletedListTitle(
                                stringResource(R.string.typesofphotoid_drivinglicence_bulletbody),
                                Text,
                            ),
                        bulletListItems =
                            persistentListOf(
                                stringResource(R.string.typesofphotoid_drivinglicence_bullet1),
                                stringResource(R.string.typesofphotoid_drivinglicence_bullet2),
                            ),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.typesofphotoid_drivinglicence_body),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    Image(
                        painter = painterResource(R.drawable.brp),
                        contentDescription = stringResource(R.string.typesofphotoid_drivinglicence_imagedescription),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            },
        )
    }
}

@Preview(heightDp = 1600)
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        TypesOfPhotoIDScreen()
    }
}
