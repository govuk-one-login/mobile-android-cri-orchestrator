package uk.gov.onelogin.criorchestrator.features.handback.internal.abort.confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import uk.gov.android.ui.patterns.loadingscreen.LoadingScreen
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internal.R

@OptIn(UnstableDesignSystemAPI::class)
@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    LoadingScreen(
        stringResource(R.string.loading),
        modifier = modifier.semantics {
            contentDescription = LOADING_SCREEN
        }
    )
}

const val LOADING_SCREEN = "Loading Screen"
