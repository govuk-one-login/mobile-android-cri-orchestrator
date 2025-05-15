package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.aborted.desktop

import androidx.activity.compose.BackHandler
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
fun AbortedReturnToDesktopWebScreen(
    viewModel: AbortedReturnToDesktopWebViewModel,
    modifier: Modifier = Modifier,
) {
    AbortedReturnToDesktopWebContent(modifier)

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun AbortedReturnToDesktopWebContent(modifier: Modifier = Modifier) {
    BackHandler(enabled = true) {
        // Do nothing as back button should be disabled from this screen
        // as the user must return to their desktop browser
    }

    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        CentreAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(AbortedReturnToDesktopWebConstants.titleId),
                    modifier =
                        Modifier
                            .padding(horizontal = horizontalPadding),
                    customContentDescription =
                        stringResource(R.string.handback_abortedreturntodesktopweb_title_content_description),
                )
            },
            body = { horizontalPadding ->
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_abortedreturntodesktopweb_body1_content_description)
                    Text(
                        text = stringResource(R.string.handback_abortedreturntodesktopweb_body1),
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
internal fun PreviewAbortedReturnToDesktopWeb() {
    GdsTheme {
        AbortedReturnToDesktopWebContent()
    }
}
