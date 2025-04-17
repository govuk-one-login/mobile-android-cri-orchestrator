package uk.gov.onelogin.criorchestrator.features.handback.internal.confirmabort

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmAbort(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "DCMAW-11935 | Android | Abort | Create DAD Abort screen")
        Text(text = "DCMAW-8801 | Android | Abort | Create MAM abort screen")
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmAbort() {
    GdsTheme {
        ConfirmAbort()
    }
}
