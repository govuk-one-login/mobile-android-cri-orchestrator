<<<<<<<< HEAD:features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/confirmabort/ConfirmNoChippedID.kt
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmabort
========
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select
>>>>>>>> e99fa2e0 (refactor: update package structure):features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/drivinglicence/select/SelectDrivingLicenceScreen.kt

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmNoChippedID(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-10691 | Android | Document Selection | NFC enabled Abort Confirmation Screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmNoChippedID() {
    GdsTheme {
        ConfirmNoChippedID()
    }
}
