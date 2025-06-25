@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.images.GdsIcon
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.v2.ErrorScreenIcon
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun UnrecoverableErrorScreen(
    viewModel: UnrecoverableErrorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                UnrecoverableErrorAction.NavigateToConfirmAbortMobile -> {
                    navController.navigate(AbortDestinations.ConfirmAbortMobile) {
                        popUpTo(AbortDestinations.ConfirmAbortMobile) {
                            inclusive = true
                        }
                    }
                }

                UnrecoverableErrorAction.NavigateToConfirmAbortDesktop ->
                    navController.navigate(AbortDestinations.ConfirmAbortDesktop) {
                        popUpTo(AbortDestinations.ConfirmAbortDesktop) {
                            inclusive = true
                        }
                    }
            }
        }
    }

    UnrecoverableErrorScreenContent(
        modifier = modifier,
        onButtonClick = viewModel::onButtonClick,
    )
}

@Suppress("LongMethod")
@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun UnrecoverableErrorScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
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
                    stringResource(UnrecoverableErrorConstants.titleId),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.handback_unrecoverableerror_body1),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.handback_unrecoverableerror_body2),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                    )
                }
            },
            secondaryButton = {
                GdsButton(
                    text = stringResource(UnrecoverableErrorConstants.buttonTextId),
                    buttonType = ButtonType.Secondary,
                    onClick = dropUnlessResumed { onButtonClick() },
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun UnrecoverableErrorScreenPreview() {
    GdsTheme {
        UnrecoverableErrorScreenContent(
            onButtonClick = {},
        )
    }
}
