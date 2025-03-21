package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations

@Composable
internal fun ContinueToProveYourIdentityScreen(
    viewModel: ContinueToProveYourIdentityViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    when (state) {
        ProveYourIdentityState.Idle -> {
            ContinueToProveYourIdentityContent(
                onContinueClick = viewModel::onContinueClick,
                modifier = modifier,
            )
        }

        ProveYourIdentityState.NfcAvailable ->
            navController.navigate(
                SelectDocumentDestinations.Passport,
            )

        ProveYourIdentityState.NfcNotAvailable ->
            navController.navigate(
                SelectDocumentDestinations.DrivingLicence,
            )
    }
}

@Composable
internal fun ContinueToProveYourIdentityContent(
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
) = CentreAlignedScreen(
    title = stringResource(R.string.continue_to_prove_your_identity_screen_title),
    body =
        persistentListOf(
            CentreAlignedScreenBodyContent.Text(
                stringResource(R.string.continue_to_prove_your_identity_screen_body),
            ),
        ),
    modifier = modifier.fillMaxSize(),
    primaryButton =
        CentreAlignedScreenButton(
            text = stringResource(R.string.continue_to_prove_your_identity_screen_button),
            onClick = onContinueClick,
        ),
)

@PreviewLightDark
@Preview(locale = "cy")
@Composable
internal fun ContinueToProveYourIdentityContentPreview() =
    GdsTheme {
        ContinueToProveYourIdentityContent(
            onContinueClick = {},
        )
    }
