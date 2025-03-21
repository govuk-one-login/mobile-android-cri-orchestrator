package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.android.ui.theme.spacingSingle
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun SelectPassportScreen(modifier: Modifier = Modifier) {
    LeftAlignedScreen(
        modifier = modifier,
        title = {
            GdsHeading(
                text = stringResource(R.string.selectdocument_passport_title),
            )
        },
        body = {
            item {
                ScreenText(
                    stringResource(R.string.selectdocument_passport_body),
                )
            }

            item {
                FullWidthImage(
                    painter = painterResource(R.drawable.nfc_passport),
                    contentDescription = stringResource(R.string.selectdocument_passport_imagedescription),
                )
            }

            item {
                ScreenText(
                    stringResource(R.string.selectdocument_passport_expiry),
                )
            }

            item {
                Box(
                    modifier = Modifier.padding(horizontal = spacingSingle),
                ) {
                    GdsButton(
                        text = stringResource(R.string.selectdocument_passport_readmore_button),
                        buttonType = ButtonType.Secondary,
                        onClick = { },
                        textAlign = TextAlign.Left,
                    )
                }
            }

            item {
                GdsSelection(
                    title =
                        RadioSelectionTitle(
                            stringResource(R.string.selectdocument_passport_title),
                            TitleType.Heading,
                        ),
                    items =
                        persistentListOf(
                            stringResource(R.string.selectdocument_passport_selection_yes),
                            stringResource(R.string.selectdocument_passport_selection_no),
                        ),
                    selectedItem = null,
                    onItemSelected = { },
                )
            }
        },
        primaryButton = {
            GdsButton(
                text = stringResource(R.string.selectdocument_passport_continuebutton),
                buttonType = ButtonType.Primary,
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        },
    )
}

// TODO Move to design system library
@Composable
private fun ScreenText(
    text: String,
    modifier: Modifier = Modifier.padding(horizontal = spacingDouble),
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) = Text(
    text = text,
    modifier = modifier,
    color = color,
    style = style,
)

// TODO Move to design system library
@Composable
private fun FullWidthImage(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillWidth,
) = Image(
    painter = painter,
    contentDescription = contentDescription,
    modifier = modifier.fillMaxWidth(),
    contentScale = contentScale,
)

@PreviewLightDark
@Composable
internal fun PreviewPassportSelectionScreen() {
    GdsTheme {
        SelectPassportScreen()
    }
}
