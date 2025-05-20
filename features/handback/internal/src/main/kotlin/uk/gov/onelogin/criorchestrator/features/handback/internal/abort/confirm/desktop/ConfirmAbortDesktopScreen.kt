package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.desktop

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.Loading
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun ConfirmAbortDesktopWebScreen(
    viewModel: ConfirmAbortDesktopViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect {
            when (it) {
                ConfirmAbortDesktopActions.NavigateToReturnToDesktop ->
                    navController.navigate(AbortDestinations.AbortedReturnToDesktopWeb)

                ConfirmAbortDesktopActions.NavigateToOfflineError ->
                    navController.navigate(
                        ErrorDestinations.RecoverableError,
                    )

                ConfirmAbortDesktopActions.NavigateToUnrecoverableError ->
                    navController.navigate(HandbackDestinations.UnrecoverableError)
            }
        }
    }

    state.let {
        when (state) {
            ConfirmAbortState.Loading -> Loading()

            ConfirmAbortState.Display ->
                ConfirmAbortDesktopWebContent(
                    onContinueClicked = viewModel::onContinueClicked,
                    modifier = modifier,
                )
        }
    }
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun ConfirmAbortDesktopWebContent(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        CentreAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = stringResource(ConfirmAbortDesktopConstants.titleId),
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    val customContentDescription =
                        stringResource(R.string.handback_confirmabort_body1_content_description)
                    Text(
                        text = stringResource(R.string.handback_confirmabort_body1),
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

                item {
                    Text(
                        text = stringResource(R.string.handback_confirmabort_body2),
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = stringResource(ConfirmAbortDesktopConstants.buttonId),
                    onClick = dropUnlessResumed { onContinueClicked() },
                    buttonType = ButtonType.Primary,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
}

@Composable
@LightDarkBothLocalesPreview
internal fun PreviewConfirmAbortDesktopWeb() {
    GdsTheme {
        ConfirmAbortDesktopWebContent(
            onContinueClicked = {},
        )
    }
}
