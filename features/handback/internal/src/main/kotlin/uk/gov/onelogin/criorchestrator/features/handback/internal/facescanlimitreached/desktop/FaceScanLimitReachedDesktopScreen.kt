package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.images.GdsIcon
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreenIcon
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun FaceScanLimitReachedDesktopScreen(
    viewModel: FaceScanLimitReachedDesktopViewModel,
    modifier: Modifier = Modifier,
) {
    BackHandler(enabled = true) {
        // Back button should be disabled from this screen
        // as the user must return to their web browser
    }

    FaceScanLimitReachedDesktopScreenContent(
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }
}

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
private fun FaceScanLimitReachedDesktopScreenContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        ErrorScreen(
            icon = { horizontalPadding ->
                GdsIcon(
                    image = ImageVector.vectorResource(ErrorScreenIcon.ErrorIcon.icon),
                    contentDescription = stringResource(ErrorScreenIcon.ErrorIcon.description),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                )
            },
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(FaceScanLimitReachedDesktopConstants.titleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    textAlign = GdsHeadingAlignment.CenterAligned,
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.handback_facescanlimitreached_body1),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_facescanlimitreacheddesktop_body2_content_description)

                    Text(
                        text = stringResource(R.string.handback_facescanlimitreacheddesktop_body2),
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

@LightDarkBothLocalesPreview
@Composable
internal fun FaceScanLimitReachedDesktopScreen() {
    GdsTheme {
        FaceScanLimitReachedDesktopScreenContent()
    }
}
