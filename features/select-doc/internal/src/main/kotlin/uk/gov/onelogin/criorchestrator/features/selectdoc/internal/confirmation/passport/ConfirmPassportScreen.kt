package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.passport

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmPassportScreen(
    viewModel: ConfirmPassportViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect {
//            navController.navigate(
//                SelectDocDestinations.
//            )
        }
    }

    ConfirmPassportScreenContent(
        title = stringResource(viewModel.titleId),
        confirmButtonText = stringResource(viewModel.buttonTextId),
        modifier = modifier,
    )
}

@Composable
internal fun ConfirmPassportScreenContent(
    title: String,
    confirmButtonText: String,
    modifier: Modifier = Modifier,
) {

}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmPassportScreen() {
    GdsTheme {
//        ConfirmPassportScreen()
    }
}
