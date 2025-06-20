@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

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
import uk.gov.onelogin.criorchestrator.features.error.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun RecoverableErrorScreen(
    viewModel: RecoverableErrorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                RecoverableErrorAction.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    RecoverableErrorScreenContent(
        modifier = modifier,
        onButtonClick = viewModel::onButtonClick,
    )
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun RecoverableErrorScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        ErrorScreen(
            title = stringResource(RecoverableErrorConstants.titleId),
            body =
                persistentListOf(
                    CentreAlignedScreenBodyContent.Text(
                        bodyText = stringResource(R.string.error_recoverableerror_body1),
                    ),
                    CentreAlignedScreenBodyContent.Text(
                        bodyText = stringResource(R.string.error_recoverableerror_body2),
                    ),
                ),
            icon = ErrorScreenIcon.ErrorIcon,
            primaryButton =
                CentreAlignedScreenButton(
                    text = stringResource(RecoverableErrorConstants.buttonTextId),
                    onClick = dropUnlessResumed { onButtonClick() },
                ),
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun RecoverableErrorScreenPreview() {
    GdsTheme {
        RecoverableErrorScreenContent(
            onButtonClick = {},
        )
    }
}
