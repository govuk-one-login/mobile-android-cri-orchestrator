@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.handback.internal.problemerror

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.patterns.errorscreen.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.ErrorScreenIcon
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ProblemErrorScreen(
    viewModel: ProblemErrorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                ProblemErrorAction.NavigateToAbort -> {
                    navController.navigate(HandbackDestinations.Abort)
                }
            }
        }
    }

    ProblemErrorScreenContent(
        modifier = modifier,
        onButtonClick = viewModel::onButtonClick,
    )
}

@OptIn(UnstableDesignSystemAPI::class)
@Suppress("LongMethod", "LongParameterList")
@Composable
internal fun ProblemErrorScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        ErrorScreen(
            title = stringResource(ProblemErrorConstants.titleId),
            body =
                persistentListOf(
                    CentreAlignedScreenBodyContent.Text(
                        bodyText = stringResource(R.string.handback_problemerror_body1),
                    ),
                    CentreAlignedScreenBodyContent.Text(
                        bodyText = stringResource(R.string.handback_problemerror_body2),
                    ),
                ),
            icon = ErrorScreenIcon.ErrorIcon,
            secondaryButton =
                CentreAlignedScreenButton(
                    text = stringResource(ProblemErrorConstants.buttonTextId),
                    onClick = onButtonClick,
                ),
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun ProblemErrorScreenPreview() {
    GdsTheme {
        ProblemErrorScreenContent(
            onButtonClick = {},
        )
    }
}
