package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
fun ReturnToMobileWebScreen(
    viewModel: ReturnToMobileWebViewModel,
    webNavigator: WebNavigator,
    modifier: Modifier = Modifier,
) {
    ReturnToMobileWebScreenContent(
        onButtonClick = viewModel::onContinueToGovUk,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(viewModel.actions) {
        viewModel.actions.collect { action ->
            when (action) {
                is ReturnToMobileWebAction.ContinueToGovUk -> {
                    webNavigator.openWebPage(action.redirectUri)
                }
            }
        }
    }
}

@Composable
private fun ReturnToMobileWebScreenContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CentreAlignedScreen(
        modifier = modifier,
        title = stringResource(ReturnToMobileWebConstants.titleId),
        body =
            persistentListOf(
                CentreAlignedScreenBodyContent.Text(stringResource(R.string.handback_returntomobileweb_body1)),
                CentreAlignedScreenBodyContent.Text(stringResource(R.string.handback_returntomobileweb_body2)),
            ),
        primaryButton =
            CentreAlignedScreenButton(
                text = stringResource(ReturnToMobileWebConstants.buttonId),
                onClick = onButtonClick,
                showIcon = true,
            ),
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewReturnToMobileWebScreen() {
    GdsTheme {
        ReturnToMobileWebScreenContent(
            onButtonClick = {},
        )
    }
}
