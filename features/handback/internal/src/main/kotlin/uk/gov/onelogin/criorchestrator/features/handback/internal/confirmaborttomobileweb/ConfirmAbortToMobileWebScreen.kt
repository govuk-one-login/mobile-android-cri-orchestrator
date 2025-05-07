package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmaborttomobileweb

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import uk.gov.android.ui.componentsv2.R.drawable.ic_external_site
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.m3_disabled
import uk.gov.android.ui.theme.m3_onDisabled
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.confirmaborttodesktopweb.ConfirmAbortToDesktopWebConstants
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmAbortToMobileWeb(modifier: Modifier = Modifier) {

    ConfirmAbortToMobileWebContent(modifier)
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun ConfirmAbortToMobileWebContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        CentreAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(ConfirmAbortToDesktopWebConstants.confirmAbortTitleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
            },
            body = { horizontalPadding ->
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_confirmabort_body1_content_description)
                    Text(
                        text = stringResource(R.string.handback_confirmabort_body1),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                            .semantics {
                                contentDescription = customContentDescription
                            }
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.handback_confirmabort_body2),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = stringResource(ConfirmAbortToMobileWebConstants.buttonId),
                    onClick = { },
                    buttonType = ButtonType.Icon(
                        buttonColors =
                            ButtonDefaults.buttonColors(
                                containerColor = colorScheme.primary,
                                contentColor = colorScheme.onPrimary,
                                disabledContainerColor = m3_disabled,
                                disabledContentColor = m3_onDisabled,
                            ),
                        fontWeight = FontWeight.Bold,
                        iconImage = ImageVector.vectorResource(ic_external_site),
                        contentDescription = "Opens in external browser"
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmAbort() {
    GdsTheme {
        ConfirmAbortToMobileWebContent()
    }
}
