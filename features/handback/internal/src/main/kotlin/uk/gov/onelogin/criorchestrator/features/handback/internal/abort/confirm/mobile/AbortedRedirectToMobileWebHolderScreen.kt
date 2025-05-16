package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator

@Composable
internal fun AbortedRedirectToMobileWebHolderScreen(
    redirectUri: String,
    webNavigator: WebNavigator,
    onFinish: () -> Unit,
) {
    LaunchedEffect(Unit) {
        webNavigator.openWebPage(redirectUri)
        onFinish()
    }
}
