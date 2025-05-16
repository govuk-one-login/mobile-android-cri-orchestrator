@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.handback.internal.unrecoverableerror

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.patterns.errorscreen.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.ErrorScreenIcon
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
            title = stringResource(UnrecoverableErrorConstants.titleId),
            body =
                persistentListOf(
                    CentreAlignedScreenBodyContent.Text(
                        bodyText = stringResource(R.string.handback_unrecoverableerror_body1),
                    ),
                    CentreAlignedScreenBodyContent.Text(
                        bodyText = stringResource(R.string.handback_unrecoverableerror_body2),
                    ),
                ),
            icon = ErrorScreenIcon.ErrorIcon,
            secondaryButton =
                CentreAlignedScreenButton(
                    text = stringResource(UnrecoverableErrorConstants.buttonTextId),
                    onClick = dropUnlessResumed { onButtonClick() },
                ),
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
