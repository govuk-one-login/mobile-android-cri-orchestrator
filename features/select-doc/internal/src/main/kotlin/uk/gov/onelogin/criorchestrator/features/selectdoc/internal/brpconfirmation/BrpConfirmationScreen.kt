package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brpconfirmation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uk.gov.android.ui.theme.GdsTheme
import uk.gov.android.ui.theme.spacingDouble

@Composable
internal fun BrpConfirmationScreen(modifier: Modifier = Modifier) {
    Text(
        "Confirm you want to continue with your BRP, BRC or FWP",
        modifier = modifier.padding(spacingDouble),
    )
}

@Preview
@Composable
internal fun PreviewBrpConfirmation() {
    GdsTheme {
        BrpConfirmationScreen()
    }
}
