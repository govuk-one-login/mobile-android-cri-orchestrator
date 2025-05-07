package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmaborttodesktopweb

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun ConfirmAbortReturnToDesktopWebScreen(modifier: Modifier = Modifier) {

    ConfirmAbortReturnToDesktopWebContent(modifier)
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun ConfirmAbortReturnToDesktopWebContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        CentreAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(ConfirmAbortToDesktopWebConstants.confirmAbortReturnTitleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
            },

            body = { horizontalPadding ->
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_confirmabortreturntodesktopweb_body1_content_description)
                    Text(
                        text = stringResource(R.string.handback_confirmabortreturntodesktopweb_body1),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                            .semantics {
                                contentDescription = customContentDescription
                            }
                    )
                }
            },
        )
    }
}

@Composable
@LightDarkBothLocalesPreview
private fun PreviewConfirmAbortReturnToDesktopWeb() {
    GdsTheme {
        ConfirmAbortReturnToDesktopWebContent()
    }
}