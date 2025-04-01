<<<<<<<< HEAD:features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/passport/confirm/ConfirmDocumentScreen.kt
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirm
========
package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation
>>>>>>>> e99fa2e0 (refactor: update package structure):features/select-doc/internal/src/main/kotlin/uk/gov/onelogin/criorchestrator/features/selectdoc/internal/passport/confirmation/ConfirmDocumentScreen.kt

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmDocumentScreen(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-8798 | Android | Document Selection |  Photo ID confirmation screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmDocumentScreen() {
    GdsTheme {
        ConfirmDocumentScreen()
    }
}
