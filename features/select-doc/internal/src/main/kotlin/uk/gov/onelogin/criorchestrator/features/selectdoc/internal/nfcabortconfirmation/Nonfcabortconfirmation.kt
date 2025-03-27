package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.nfcabortconfirmation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun NoNfcAbortConfirmationScreen(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-10678 | Android | Document Selection | No NFC Abort Confirmation Screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewNoNfcAbortConfirmationScreen() {
    GdsTheme {
        NoNfcAbortConfirmationScreen()
    }
}
