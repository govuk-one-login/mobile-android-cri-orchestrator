<<<<<<<< HEAD:features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/brp/confirmation/ConfirmBrpScreen.kt
<<<<<<<< HEAD:features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/brp/confirm/ConfirmBrpScreen.kt
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm
========
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirmation
>>>>>>>> dc297a4e (fix: rebase issues):features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/brp/confirmation/ConfirmBrpScreen.kt
========
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp.confirm
>>>>>>>> 1233ac6e (refactor: use active tense confirm instead of confirmation):features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/brp/confirm/ConfirmBrpScreen.kt

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
