package uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop

import androidx.activity.compose.BackHandler
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.patterns.errorscreen.ErrorScreen
import uk.gov.android.ui.patterns.errorscreen.ErrorScreenIcon
import uk.gov.android.ui.patterns.loadingscreen.LoadingScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.error.internalapi.nav.ErrorDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.ConfirmAbortState
import uk.gov.onelogin.criorchestrator.features.handback.internal.facescanlimitreached.desktop.FaceScanLimitReachedDesktopConstants.titleId
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.HandbackDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun FaceScanLimitReachedDesktopScreen(
    viewModel: FaceScanLimitReachedDesktopViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    BackHandler {
        // Do nothing since user shouldn't be able to go back as they have already exited the SDK.
    }

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect {
            when (it) {
                FaceScanLimitReachedDesktopActions.NavigateToReturnToDesktop ->
                    navController.navigate(AbortDestinations.AbortedReturnToDesktopWeb)

                FaceScanLimitReachedDesktopActions.NavigateToOfflineError ->
                    navController.navigate(
                        ErrorDestinations.RecoverableError,
                    )

                FaceScanLimitReachedDesktopActions.NavigateToUnrecoverableError ->
                    navController.navigate(HandbackDestinations.UnrecoverableError)
            }
        }
    }

    when (state) {
        ConfirmAbortState.Loading -> LoadingScreen()

        ConfirmAbortState.Display ->
            FaceScanLimitReachedDesktopContent(
                onButtonClick = viewModel::onButtonClicked,
                modifier = modifier,
            )
    }
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun FaceScanLimitReachedDesktopContent(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = colorScheme.background,
    ) {
        ErrorScreen(
            icon = ErrorScreenIcon.ErrorIcon,
            title = stringResource(titleId),
            primaryButton = CentreAlignedScreenButton(
                text = stringResource(FaceScanLimitReachedDesktopConstants.buttonId),
                onClick = dropUnlessResumed { onButtonClick() },
            ),
        )
    }
}

@Composable
@LightDarkBothLocalesPreview
internal fun PreviewFaceScanLimitReachedDesktopContent() {
    GdsTheme {
        FaceScanLimitReachedDesktopContent(
            onButtonClick = {},
        )
    }
}
