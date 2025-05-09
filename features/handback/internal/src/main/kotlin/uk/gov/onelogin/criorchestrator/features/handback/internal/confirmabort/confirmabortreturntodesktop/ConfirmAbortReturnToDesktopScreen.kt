package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort.confirmabortreturntodesktop

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun ConfirmAbortReturnToDesktopWebScreen(
    viewModel: ConfirmAbortReturnToDesktopViewModel,
    modifier: Modifier = Modifier,
) {
    ConfirmAbortReturnToDesktopWebContent(modifier)

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }
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
                    text = stringResource(ConfirmAbortReturnToDesktopConstants.titleId),
                    modifier =
                        Modifier
                            .padding(horizontal = horizontalPadding),
                    customContentDescription =
                        stringResource(R.string.handback_confirmabortreturntodesktopweb_title_content_description),
                )
            },
            body = { horizontalPadding ->
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_confirmabortreturntodesktopweb_body1_content_description)
                    Text(
                        text = stringResource(R.string.handback_confirmabortreturntodesktopweb_body1),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding)
                                .semantics {
                                    contentDescription = customContentDescription
                                },
                    )
                }
            },
        )
    }
}

@Composable
@LightDarkBothLocalesPreview
internal fun PreviewConfirmAbortReturnToDesktopWeb() {
    GdsTheme {
        ConfirmAbortReturnToDesktopWebContent()
    }
}
