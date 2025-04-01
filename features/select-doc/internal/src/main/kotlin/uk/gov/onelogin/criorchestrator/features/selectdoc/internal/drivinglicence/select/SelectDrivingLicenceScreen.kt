package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun SelectDrivingLicenceScreen(modifier: Modifier = Modifier) {
    Text(
        text = "DCMAW-8099 | Android | Document Selection | Do you have a driving licence screen",
        modifier = modifier,
    )
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewDrivingLicenceScreen() {
    GdsTheme {
        SelectDrivingLicenceScreen()
    }
}
