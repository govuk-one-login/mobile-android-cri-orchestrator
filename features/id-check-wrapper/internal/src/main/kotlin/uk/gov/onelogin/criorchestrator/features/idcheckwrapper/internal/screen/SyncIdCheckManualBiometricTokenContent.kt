package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.inputs.radio.GdsSelection
import uk.gov.android.ui.componentsv2.inputs.radio.RadioSelectionTitle
import uk.gov.android.ui.componentsv2.inputs.radio.TitleType
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SyncIdCheckManualBiometricTokenContent(
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by rememberSaveable { mutableStateOf<Int?>(null) }
    Column(modifier = modifier) {
        GdsSelection(
            title =
                RadioSelectionTitle(
                    "Select get biometric token result",
                    titleType = TitleType.BoldText,
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
