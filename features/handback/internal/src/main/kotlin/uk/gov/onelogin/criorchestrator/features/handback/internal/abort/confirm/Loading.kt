package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import uk.gov.android.ui.patterns.loadingscreen.LoadingScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun Loading(modifier: Modifier = Modifier) {
    LoadingScreen(
        stringResource(R.string.loading),
        modifier = modifier.testTag(LOADING_SCREEN),
    )
}

const val LOADING_SCREEN = "Loading Screen"

@Composable
@LightDarkBothLocalesPreview
internal fun PreviewLoading() {
    GdsTheme {
        Loading()
    }
}
