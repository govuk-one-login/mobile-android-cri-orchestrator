package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.brp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SelectBRPScreen(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-10690 | Android | Document Selection | Do you have a BRP screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewTypesOfPhotoIDScreen() {
    GdsTheme {
        SelectBRPScreen()
    }
}
