package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun TypesOfPhotoIDScreen(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-8796 | Android | Document Selection | NFC enabled Photo ID Information Screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        TypesOfPhotoIDScreen()
    }
}
