<<<<<<<< HEAD:features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/brp/confirm/ConfirmBrpScreen.kt
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm
========
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirmation
>>>>>>>> d560d7e5 (fix: rebase issues):features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/brp/confirmation/ConfirmBrpScreen.kt

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.GdsTheme
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmBrpScreen(modifier: Modifier = Modifier) {
    Text(
        "Confirm you want to continue with your BRP, BRC or FWP",
        modifier = modifier.padding(spacingDouble),
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun ConfirmBrpPreview() {
    GdsTheme {
        ConfirmBrpScreen()
    }
}
