package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                ContinueToProveYourIdentityAction.NavigateToPassport ->
                    navController.navigate(
                        SelectDocumentDestinations.Passport,
                    )

                ContinueToProveYourIdentityAction.NavigateToDrivingLicense ->
                    navController.navigate(
                        SelectDocumentDestinations.DrivingLicence,
                    )
            }
        }
    }

    ContinueToProveYourIdentityContent(
        onContinueClick = viewModel::onContinueClick,
        modifier = modifier,
    )
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
