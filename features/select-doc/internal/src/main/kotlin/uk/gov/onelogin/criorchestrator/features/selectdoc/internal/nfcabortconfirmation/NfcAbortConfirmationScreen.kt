package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.nfcabortconfirmation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun NfcAbortConfirmationScreen(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-10691 | Android | Document Selection | NFC enabled Abort Confirmation Screen",
        modifier = modifier,
    )

}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewNfcAbortConfirmationScreen() {
    GdsTheme {
        NfcAbortConfirmationScreen()
    }
}
