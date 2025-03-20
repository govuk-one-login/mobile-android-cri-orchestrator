package uk.gov.onelogin.criorchestrator.libraries.composeutils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Preview(locale = "cy", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(locale = "cy")
@PreviewLightDark
annotation class LightDarkBothLocalesPreviews

@LightDarkBothLocalesPreviews
@Composable
internal fun ExampleBothLocalesPreview() {
    Text("Hello World")
}