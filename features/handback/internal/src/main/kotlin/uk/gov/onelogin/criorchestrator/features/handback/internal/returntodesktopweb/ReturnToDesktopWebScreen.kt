package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun ReturnToDesktopWebScreen(modifier: Modifier = Modifier) {
    ReturnToDesktopWebScreenContent(
        modifier = modifier,
    )
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun ReturnToDesktopWebScreenContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        CentreAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(ReturnToDesktopWebConstants.titleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    textAlign = GdsHeadingAlignment.CenterAligned,
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.handback_returntodesktopweb_body1),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.handback_returntodesktopweb_body2),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewReturnToMobileWebScreen() {
    GdsTheme {
        ReturnToDesktopWebScreenContent()
    }
}
