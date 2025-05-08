package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun ReturnToDesktopWebScreen(
    viewModel: ReturnToDesktopWebViewModel,
    reviewRequester: ReviewRequester,
    modifier: Modifier = Modifier,
) {
    BackHandler(enabled = true) {
        // Back button should be disabled from this screen
        // as the user must return to their desktop browser
    }

    ReturnToDesktopWebScreenContent(
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    val activity = LocalContext.current.findAndroidActivity()
    LaunchedEffect(viewModel.actions) {
        viewModel.actions.collect { action ->
            when (action) {
                is ReturnToDesktopWebAction.RequestReview -> {
                    activity?.let {
                        reviewRequester.requestReview(it)
                    }
                }
            }
        }
    }
}

private fun Context.findAndroidActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
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
                    customContentDescription =
                        stringResource(R.string.handback_returntodesktopweb_title_content_description),
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
                    val customContentDescription =
                        stringResource(R.string.handback_returntodesktopweb_body2_content_description)

                    Text(
                        text = stringResource(R.string.handback_returntodesktopweb_body2),
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
internal fun PreviewReturnToDesktopWebScreen() {
    GdsTheme {
        ReturnToDesktopWebScreenContent()
    }
}
