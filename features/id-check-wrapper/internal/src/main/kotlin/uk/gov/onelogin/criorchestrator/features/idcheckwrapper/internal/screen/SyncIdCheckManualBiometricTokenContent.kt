package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.heading.GdsHeadingStyle
import uk.gov.android.ui.componentsv2.inputs.radio.GdsRadios
import uk.gov.android.ui.componentsv2.inputs.radio.GdsRadiosTitle
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SyncIdCheckManualBiometricTokenContent(
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by rememberSaveable { mutableStateOf<Int?>(null) }
    Surface(modifier = modifier) {
        GdsRadios(
            title =
                GdsRadiosTitle(
                    "Select biometric token result",
                    style = GdsHeadingStyle.Body,
                    fontWeight = FontWeight.Bold,
                ),
            items =
                persistentListOf(
                    "Success",
                    "Recoverable error",
                    "Unrecoverable error",
                ),
            selectedItem = selectedItem,
            onItemSelected = {
                onItemSelected(it)
                selectedItem = it
            },
            modifier = Modifier.padding(horizontal = spacingDouble),
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewBiometricTokenDebug() {
    GdsTheme {
        SyncIdCheckManualBiometricTokenContent(
            onItemSelected = {},
        )
    }
}
