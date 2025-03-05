package uk.gov.onelogin.criorchestrator.libraries.screenshottesting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
internal fun ProvidesContentWithFakeViewModelStoreOwner(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalViewModelStoreOwner provides FakeViewModelStoreOwner()) {
        content()
    }
}

private class FakeViewModelStoreOwner(
    override val viewModelStore: ViewModelStore = ViewModelStore(),
) : ViewModelStoreOwner
