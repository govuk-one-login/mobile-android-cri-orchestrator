package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.passport

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmPassportScreen(
    viewModel: ConfirmPassportViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "DCMAW-8798 | Android | Document Selection |  Photo ID confirmation screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmPassportScreen() {
    GdsTheme {
        ConfirmPassportScreen()
    }
}
